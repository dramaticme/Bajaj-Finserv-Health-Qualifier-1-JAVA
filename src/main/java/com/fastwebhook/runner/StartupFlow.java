package com.fastwebhook.runner;

import com.fastwebhook.config.CandidateProperties;
import com.fastwebhook.config.HealthRxProperties;
import com.fastwebhook.http.Dto.GenerateWebhookRequest;
import com.fastwebhook.http.Dto.GenerateWebhookResponse;
import com.fastwebhook.http.Dto.SubmitRequest;
import com.fastwebhook.sql.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class StartupFlow implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupFlow.class);

    private final CandidateProperties candidate;
    private final HealthRxProperties healthrx;
    private final WebClient webClient;

    public StartupFlow(CandidateProperties candidate, HealthRxProperties healthrx) {
        this.candidate = candidate;
        this.healthrx = healthrx;
        this.webClient = WebClient.builder()
                .baseUrl(healthrx.getBaseUrl())
                .build();
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Starting fast flow…");

        // ---------------------------
        // 1) Generate webhook
        // ---------------------------
        GenerateWebhookResponse gen = webClient.post()
                .uri(healthrx.getGeneratePath())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new GenerateWebhookRequest(
                        candidate.getName(),
                        candidate.getRegNo(),
                        candidate.getEmail()))
                .retrieve()
                .bodyToMono(GenerateWebhookResponse.class)
                .onErrorResume(e -> {
                    log.error("Failed to generate webhook: {}", e.toString());
                    return Mono.empty();
                })
                .block();

        if (gen == null || gen.getAccessToken() == null) {
            log.error("No response or accessToken from generateWebhook. Aborting.");
            return;
        }

        log.info("Got webhook URL: {}", gen.getWebhook());
        log.info("Got accessToken: {}{}", (healthrx.isUseBearerPrefix() ? "Bearer " : ""), "***");

        // ---------------------------
        // 2) Pick SQL based on regNo
        // ---------------------------
        String finalSql = pickSql(candidate.getRegNo());
        log.info("Chosen SQL (first 120 chars): {}", 
                 finalSql.length() > 120 ? finalSql.substring(0,120) + "…" : finalSql);

        // ---------------------------
        // 3) Submit SQL to webhook
        // ---------------------------
        String submitUrl = gen.getWebhook();
        boolean absolute = true;

        if (submitUrl == null || submitUrl.isBlank()) {
            submitUrl = healthrx.getFallbackSubmitPath();
            absolute = false; // resolve against baseUrl
            log.warn("Webhook URL missing in response. Falling back to {}", submitUrl);
        }

        WebClient submitClient = absolute ? WebClient.create() : this.webClient;
        WebClient.RequestBodySpec req = submitClient.post().uri(submitUrl);

        String authHeader = healthrx.isUseBearerPrefix()
                ? "Bearer " + gen.getAccessToken()
                : gen.getAccessToken();

        String result = req
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authHeader)
                .bodyValue(new SubmitRequest(finalSql))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Submission failed: {}", e.toString()))
                .onErrorReturn("SUBMISSION_FAILED")
                .block();

        log.info("Submission response: {}", result);
        log.info("Done.");
    }

    /**
     * Pick SQL based on the last two digits of the registration number.
     * Odd → Q1_SQL, Even → Q2_SQL
     */
    private String pickSql(String regNo) {
        if (regNo == null || regNo.isBlank()) return Queries.Q1_SQL; // default

        String digits = regNo.replaceAll("\\D+", ""); // keep only numbers

        if (digits.length() < 2) {
            // If only one digit is present, treat it as last two digits anyway
            int d = Integer.parseInt(digits);
            return (d % 2 == 1) ? Queries.Q1_SQL : Queries.Q2_SQL;
        }

        int lastTwo = Integer.parseInt(digits.substring(digits.length() - 2));
        return (lastTwo % 2 == 1) ? Queries.Q1_SQL : Queries.Q2_SQL;
    }
}

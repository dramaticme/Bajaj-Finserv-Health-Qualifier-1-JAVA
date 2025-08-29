package com.fastwebhook.http;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dto {

  public static class GenerateWebhookRequest {
    private String name;
    private String regNo;
    private String email;

    public GenerateWebhookRequest() {}

    public GenerateWebhookRequest(String name, String regNo, String email) {
      this.name = name;
      this.regNo = regNo;
      this.email = email;
    }

    public String getName() { return name; }
    public String getRegNo() { return regNo; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public void setEmail(String email) { this.email = email; }
  }

  public static class GenerateWebhookResponse {
    // API may return "webhook" or "webhookUrl" – support both
    @JsonProperty("webhook") @JsonAlias({"webhookUrl","url"})
    private String webhook;
    @JsonProperty("accessToken") @JsonAlias({"token","jwt"})
    private String accessToken;

    public String getWebhook() { return webhook; }
    public String getAccessToken() { return accessToken; }

    public void setWebhook(String webhook) { this.webhook = webhook; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
  }

  public static class SubmitRequest {
    @JsonProperty("finalQuery")
    private String finalQuery;

    public SubmitRequest() {}
    public SubmitRequest(String finalQuery) { this.finalQuery = finalQuery; }

    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
  }
}

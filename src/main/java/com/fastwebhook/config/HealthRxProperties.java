package com.fastwebhook.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "healthrx")
public class HealthRxProperties {
  private String baseUrl;
  private String generatePath;
  private String fallbackSubmitPath;
  private boolean useBearerPrefix;

  public String getBaseUrl() { return baseUrl; }
  public String getGeneratePath() { return generatePath; }
  public String getFallbackSubmitPath() { return fallbackSubmitPath; }
  public boolean isUseBearerPrefix() { return useBearerPrefix; }

  public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
  public void setGeneratePath(String generatePath) { this.generatePath = generatePath; }
  public void setFallbackSubmitPath(String fallbackSubmitPath) { this.fallbackSubmitPath = fallbackSubmitPath; }
  public void setUseBearerPrefix(boolean useBearerPrefix) { this.useBearerPrefix = useBearerPrefix; }
}

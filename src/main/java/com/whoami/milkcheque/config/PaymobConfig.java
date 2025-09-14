package com.whoami.milkcheque.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "paymob")
public class PaymobConfig {
  private String apiKey;
  private Integer integrationId;
  private Integer iframeId;

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public Integer getIntegrationId() {
    return integrationId;
  }

  public void setIntegrationId(Integer integrationId) {
    this.integrationId = integrationId;
  }

  public Integer getIframeId() {
    return iframeId;
  }

  public void setIframeId(Integer iframeId) {
    this.iframeId = iframeId;
  }
}

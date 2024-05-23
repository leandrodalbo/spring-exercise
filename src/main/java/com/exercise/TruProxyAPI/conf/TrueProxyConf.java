package com.exercise.TruProxyAPI.conf;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tru-proxy")
public record TrueProxyConf(String endpointUri, String apiKey) {
}
package com.exercise.TruProxyAPI.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConf {

    @Bean
    WebClient webClient(WebClient.Builder builder, TrueProxyConf trueProxyConf) {
        return builder.baseUrl(trueProxyConf.endpointUri())
                .build();
    }
}

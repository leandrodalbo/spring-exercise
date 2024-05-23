package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.conf.TrueProxyConf;
import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyItemDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
public class TruProxyCallService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);

    private final WebClient webClient;
    private final TrueProxyConf trueProxyConf;

    public TruProxyCallService(WebClient webClient, TrueProxyConf trueProxyConf) {
        this.webClient = webClient;
        this.trueProxyConf = trueProxyConf;
    }

    public List<TruProxyCompanyItemDto> findCompanies(String input) {
        return Objects.requireNonNull(webClient
                .get()
                .uri("/Search?Query=" + input)
                .header("x-api-key", trueProxyConf.apiKey())
                .retrieve()
                .bodyToMono(TruProxyCompanyDto.class)
                .block(REQUEST_TIMEOUT)).items();
    }

    public List<TruProxyOfficerItemDto> findOfficers(String company) {
        return Objects.requireNonNull(webClient
                .get()
                .uri("/Officers?CompanyNumber=" + company)
                .header("x-api-key", trueProxyConf.apiKey())
                .retrieve()
                .bodyToMono(TruProxyOfficerDto.class)
                .block(REQUEST_TIMEOUT)).items();
    }
}

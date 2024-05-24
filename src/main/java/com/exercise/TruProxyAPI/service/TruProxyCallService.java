package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.conf.TrueProxyConf;
import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyCompanyItemDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerDto;
import com.exercise.TruProxyAPI.proxyDto.TruProxyOfficerItemDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
public class TruProxyCallService {

    private final WebClient webClient;
    private final TrueProxyConf trueProxyConf;

    public TruProxyCallService(WebClient webClient, TrueProxyConf trueProxyConf) {
        this.webClient = webClient;
        this.trueProxyConf = trueProxyConf;
    }

    public List<TruProxyCompanyItemDto> findCompanies(String input) {

        var result = webClient
                .get()
                .uri("/Search?Query=" + input)
                .header("x-api-key", trueProxyConf.apiKey())
                .retrieve()
                .bodyToMono(TruProxyCompanyDto.class)
                .block();

        var items = Objects.requireNonNull(result).items();

        return items;
    }

    public List<TruProxyOfficerItemDto> findOfficers(String company) {
        var results = webClient
                .get()
                .uri("/Officers?CompanyNumber=" + company)
                .header("x-api-key", trueProxyConf.apiKey())
                .retrieve()
                .bodyToMono(TruProxyOfficerDto.class)
                .block();

        var items = Objects.requireNonNull(results).items().stream().filter(officerItemDto -> officerItemDto.resigned_on() == null).toList();
        return items;
    }
}

package com.exercise.TruProxyAPI.service;

import com.exercise.TruProxyAPI.conf.TrueProxyConf;
import com.exercise.TruProxyAPI.proxyDto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TruProxyCallServiceTest {

    ObjectMapper mapper = new ObjectMapper();
    private MockWebServer mockWebServer;
    private TruProxyCallService service;

    @BeforeEach
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();

        var webClient = WebClient.builder()
                .baseUrl(String.format("http://localhost:%s",
                        mockWebServer.getPort()))
                .build();
        this.service = new TruProxyCallService(webClient, new TrueProxyConf("http://localhost:%s", "AS4F4A323ZG4A4E3SF"));
    }

    @AfterEach
    void clean() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void shouldFindCompanies() throws JsonProcessingException {
        var input = "1234567890";
        TruProxyCompanyDto responseBody = new TruProxyCompanyDto(20, List.of(new TruProxyCompanyItemDto(
                input, "any", "any", "active", "22/02/2020", new TruProxyAddressDto("London", "HD3 3DW", "ANY", "ANY", "Italy"))));

        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(responseBody));

        mockWebServer.enqueue(mockResponse);

        assertThat(service.findCompanies(input).get(0).company_number()).isEqualTo(input);


    }

    @Test
    void shouldFindOfficers() throws JsonProcessingException {
        var input = "1234567890";
        TruProxyOfficerDto responseBody = new TruProxyOfficerDto(20, List.of(new TruProxyOfficerItemDto(
                "Leandro", "any", "any", null, new TruProxyAddressDto("London", "HD3 3DW", "ANY", "ANY", "Italy"))));

        var mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(responseBody));

        mockWebServer.enqueue(mockResponse);

        assertThat(service.findOfficers(input).get(0).name()).isEqualTo("Leandro");


    }
}

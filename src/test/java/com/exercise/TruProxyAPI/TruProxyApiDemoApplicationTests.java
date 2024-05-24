package com.exercise.TruProxyAPI;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TruProxyApiDemoApplicationTests {

    private static WireMockServer wireMockServer;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeAll
    static void init() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
        configureFor("localhost", 8081);
    }


    @Test
    void shouldFetchCompanyInfo() throws Exception {
        /*

        stubFor(get(urlMatching("/Search"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                ));

        stubFor(get(urlMatching("/Officers"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                ));

        restTemplate.getForObject("/api?companyNumber=06500244", ProxySearchResult.class);


        */
    }
}

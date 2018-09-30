package com.meli.mutants.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

@RunWith(SpringRunner.class)
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsControllerIT {

    @LocalServerPort
    private Long port;

    private WebTestClient webClient;

    @Before
    public void before() {
        if (webClient == null) {
            webClient = WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + port)
                    .responseTimeout(Duration.ofSeconds(10))
                    .build();
        }
    }

    @Test
    public void getStats() {
        webClient.get()
                .uri("/stats")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }
}
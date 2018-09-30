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

import static com.meli.mutants.utils.DnaMockUtils.mockDna;
import static org.assertj.core.util.Arrays.array;

@RunWith(SpringRunner.class)
@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantControllerIT {

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
    public void analyzeMutantDna() {
        String[] sequences = array("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        webClient.post()
                .uri("/mutant")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(mockDna(sequences))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void analyzeHumanDna() {
        String[] sequences = array("ACTCAA","CAGTTC","CGATGT","AGCTGG","CTGCTA","TCACTG");

        webClient.post()
                .uri("/mutant")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(mockDna(sequences))
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    public void analyzeInvalidDnaNullSequences() {
        webClient.post()
                .uri("/mutant")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(mockDna(null))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void analyzeInvalidDnaOneNullSequence() {
        String[] sequences = array(null,"CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        webClient.post()
                .uri("/mutant")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(mockDna(sequences))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void analyzeInvalidDnaSize() {
        String[] sequences = array("AGTGC","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        webClient.post()
                .uri("/mutant")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(mockDna(sequences))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void analyzeInvalidDnaCharacter() {
        String[] sequences = array("XXXXXX","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG");

        webClient.post()
                .uri("/mutant")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(mockDna(sequences))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}

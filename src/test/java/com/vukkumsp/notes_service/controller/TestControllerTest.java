package com.vukkumsp.notes_service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

//    @Test
//    void testGetTestEndpoint(){
//        webTestClient.get()
//                .uri("/api/test")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class)
//                .isEqualTo("test");
//    }
}

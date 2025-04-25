package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import com.tenpo.challenge.service.LogCallHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest(LogCallHistoryController.class)
public class LogCallHistoryControllerTest {

    @MockitoBean
    private LogCallHistoryService logCallHistoryService;

    private WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(new LogCallHistoryController(logCallHistoryService)).build();
    }

    @Test
    public void logCallHistory_getAll() {
        LogCallHistoryDto dto1 = new LogCallHistoryDto();
        LogCallHistoryDto dto2 = new LogCallHistoryDto();

        when(logCallHistoryService.getAll(PageRequest.of(0, 10))).thenReturn(Flux.just(dto1, dto2));

        webTestClient.get()
                .uri("/api/v1/call-history")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LogCallHistoryDto.class).contains(dto1, dto2);

    }

}

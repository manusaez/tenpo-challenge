package com.tenpo.challenge.controller;

import com.tenpo.challenge.client.PercentageApiClient;
import com.tenpo.challenge.dto.CalculatorRequestDto;
import com.tenpo.challenge.dto.CalculatorResponseDto;
import com.tenpo.challenge.filter.LogCallHistoryFilter;
import com.tenpo.challenge.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(CalculatorController.class)
public class CalculatorControllerTest {

    @Mock
    private PercentageApiClient percentageApiClient;

    @MockitoBean
    private CalculatorService calculatorService;

    @MockitoBean
    private LogCallHistoryFilter logCallHistoryFilter;

    private WebTestClient webTestClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(new CalculatorController(calculatorService)).build();
    }

    @Test
    public void calculatorController_sum() {

        CalculatorRequestDto requestDto = mock(CalculatorRequestDto.class);
        requestDto.setNum1(20.0f);
        requestDto.setNum2(10.0f);
        Integer[] arr = {10};
        when(percentageApiClient.getPercentage()).thenReturn(Mono.just(arr));

        CalculatorResponseDto responseDto = CalculatorResponseDto.builder()
                .num1(20.0f)
                .num2(10.0f)
                .result(33.0f)
                .percentage(10.0f)
                .build();

        when(calculatorService.sum(requestDto)).thenReturn(Mono.just(responseDto));

        HttpStatusCode resp = webTestClient.post()
                .uri("/api/v1/calculator")
                .body(Mono.just(requestDto), CalculatorRequestDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CalculatorResponseDto.class).returnResult().getStatus();

        assertEquals(200, resp.value());
    }

}

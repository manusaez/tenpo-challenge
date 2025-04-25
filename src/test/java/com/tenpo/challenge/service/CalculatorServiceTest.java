package com.tenpo.challenge.service;

import com.tenpo.challenge.client.PercentageApiClient;
import com.tenpo.challenge.dto.CalculatorRequestDto;
import com.tenpo.challenge.dto.CalculatorResponseDto;
import com.tenpo.challenge.exception.MaxRateLimitException;
import com.tenpo.challenge.exception.PercentageNotFoundException;
import com.tenpo.challenge.filter.LogCallHistoryFilter;
import com.tenpo.challenge.service.impl.CalculatorServiceImpl;
import io.github.resilience4j.ratelimiter.*;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatorServiceTest {

    private PercentageApiClient percentageApiClient;
    private CalculatorServiceImpl calculatorService;

    @MockitoBean
    private LogCallHistoryFilter logCallHistoryFilter;

    private RateLimiter limit;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        RateLimiterConfig config = RateLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5))
                .limitRefreshPeriod(Duration.ofNanos(500))
                .limitForPeriod(5)
                .build();
        limit = mock(RateLimiter.class);

        given(limit.getRateLimiterConfig()).willReturn(config);

        percentageApiClient = mock(PercentageApiClient.class);
        calculatorService = new CalculatorServiceImpl(percentageApiClient);
    }

    @Test
    void calculatorService_successfully() {
        CalculatorRequestDto requestDto = new CalculatorRequestDto();
        requestDto.setNum1(20.0f);
        requestDto.setNum2(10.0f);
        Integer[] arr = {10};

        CalculatorResponseDto responseDto = CalculatorResponseDto.builder()
                .num1(20.0f)
                .num2(10.0f)
                .result(33.0f)
                .percentage(10.0f)
                .build();

        when(percentageApiClient.getPercentage()).thenReturn(Mono.just(arr));
        Mono<CalculatorResponseDto> result = calculatorService.sum(requestDto);

        StepVerifier.create(result)
                .expectNext(responseDto)
                .verifyComplete();

    }

    @Test
    void calculatorService_noPercentage() {
        CalculatorRequestDto dto = mock(CalculatorRequestDto.class);
        when(dto.getNum1()).thenReturn(20.0f);
        when(dto.getNum2()).thenReturn(10.0f);
        Integer[] arr = new Integer[0];

        when(percentageApiClient.getPercentage()).thenReturn(Mono.just(arr));

        Mono<CalculatorResponseDto> result = calculatorService.sum(dto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof PercentageNotFoundException)
                .verify();


    }

    @Test
    public void calculatorService_RateLimiter() {
        Integer[] arr = {10};
        when(percentageApiClient.getPercentage()).thenReturn(Mono.just(arr));
        CalculatorRequestDto request = new CalculatorRequestDto();
        request.setNum1(20.0f);
        request.setNum2(10.0f);

        Function<CalculatorRequestDto, Mono<CalculatorResponseDto>> decorated = RateLimiter.decorateFunction(limit, calculatorService::sum);

        given(limit.acquirePermission(1)).willReturn(false);
        Try<Mono<CalculatorResponseDto>> decoratedFunctionResult = Try.success(request).mapTry(decorated::apply);
        assertThat(decoratedFunctionResult.isFailure()).isTrue();
        assertThat(decoratedFunctionResult.getCause()).isInstanceOf(RequestNotPermitted.class);

        given(limit.acquirePermission(1)).willReturn(true);
        Try<Mono<CalculatorResponseDto>> secondFunctionResult = Try.success(request).mapTry(decorated::apply);
        assertThat(secondFunctionResult.isSuccess()).isTrue();
    }

    @Test
    void calculatorService_testFallbackRateLimiter() {
        calculatorService.fallbackRateLimiter(new RuntimeException())
                .doOnError(e -> assertThat(e instanceof MaxRateLimitException).isTrue())
                .subscribe();

    }

}

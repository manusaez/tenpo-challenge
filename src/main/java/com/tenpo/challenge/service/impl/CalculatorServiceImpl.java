package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.client.PercentageApiClient;
import com.tenpo.challenge.dto.CalculatorRequestDto;
import com.tenpo.challenge.dto.CalculatorResponseDto;
import com.tenpo.challenge.exception.MaxRateLimitException;
import com.tenpo.challenge.exception.PercentageNotFoundException;
import com.tenpo.challenge.service.CalculatorService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorServiceImpl implements CalculatorService {

    private final PercentageApiClient percentageApiClient;

    @Override
    @RateLimiter(name = "rateLimiterPercentage", fallbackMethod = "fallbackRateLimiter")
    public Mono<CalculatorResponseDto> sum(CalculatorRequestDto request) {

        return percentageApiClient.getPercentage().map(percentage -> {
            if(percentage.length > 0) {
                return this.doTheSum(percentage[0], request.getNum1(), request.getNum2());
            } else {
                throw new PercentageNotFoundException("Percentage not found");
            }
        });
    }

    private CalculatorResponseDto doTheSum(float percentaje, float num1, float num2) {
        log.info("Performing calculation. ({} + {}) + (({} + {}) * {} / 100)", num1, num2, num1, num2, percentaje);
        float sum = Float.sum(num1, num2);
        float result = Float.sum(sum, (sum * percentaje / 100));
        log.info("Result: {}", result);
        return CalculatorResponseDto.builder()
                .num1(num1)
                .num2(num2)
                .result(result)
                .percentage(percentaje)
                .build();
    }

    public Mono<CalculatorResponseDto> fallbackRateLimiter(Throwable t) {
        return Mono.error(new MaxRateLimitException("The service does not permit more than 3 requests per minute."));
    }
}

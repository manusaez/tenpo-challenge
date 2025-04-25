package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.CalculatorRequestDto;
import com.tenpo.challenge.dto.CalculatorResponseDto;
import reactor.core.publisher.Mono;

public interface CalculatorService {
    Mono<CalculatorResponseDto> sum(CalculatorRequestDto request);
}

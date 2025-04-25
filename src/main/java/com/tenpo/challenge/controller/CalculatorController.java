package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.CalculatorRequestDto;
import com.tenpo.challenge.dto.CalculatorResponseDto;
import com.tenpo.challenge.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Operation(summary = "Sum between two numbers with dynamic percentage")
    @ApiResponse(responseCode = "200", description = "Sum performed correctly.")
    @ApiResponse(responseCode = "400", description = "Incorrect input parameters.")
    @ApiResponse(responseCode = "404", description = "Resource ot found.")
    @ApiResponse(responseCode = "429", description = "Too many requests per minute. 3 requests max.")
    @ApiResponse(responseCode = "500", description = "Problem at the server.")
    @ApiResponse(responseCode = "503", description = "Service unavailable.")
    @PostMapping("/calculator")
    public ResponseEntity<Mono<CalculatorResponseDto>> calculator(@Valid @RequestBody CalculatorRequestDto request) {
        return ResponseEntity.ok().body(calculatorService.sum(request));
    }

}

package com.tenpo.challenge.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@RequiredArgsConstructor
public class CalculatorRequestDto {

    @NotNull(message = "num1 required")
    private Float num1;

    @NotNull(message = "num2 required")
    private Float num2;
}

package com.tenpo.challenge.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculatorResponseDto {
    private Float num1;
    private Float num2;
    private Float result;
    private Float percentage;
}

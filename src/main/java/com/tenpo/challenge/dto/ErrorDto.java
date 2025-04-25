package com.tenpo.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDto {
    private LocalDateTime timestamp;
    private String message;
    private int errorCode;

}

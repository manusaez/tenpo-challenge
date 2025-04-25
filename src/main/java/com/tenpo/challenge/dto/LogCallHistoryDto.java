package com.tenpo.challenge.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogCallHistoryDto {
    private Long id;
    private LocalDateTime timestamp;
    private String endpoint;
    private String request;
    private String response;
    private int statusCode;
}

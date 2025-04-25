package com.tenpo.challenge.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logging_call_history")
public class LogCallHistoryEntity {

    @Id
    private Long id;

    private LocalDateTime timestamp;
    private String endpoint;
    private String request;
    private String response;

    @Column("status_code")
    private int statusCode;

}
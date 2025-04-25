package com.tenpo.challenge.filter;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import com.tenpo.challenge.service.LogCallHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class LogCallHistoryFilter implements WebFilter {

    private final LogCallHistoryService service;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        BodyCaptureExchange bodyCaptureExchange = new BodyCaptureExchange(exchange);

        return chain.filter(bodyCaptureExchange).doOnSuccess((s) -> {
            String requestBody = bodyCaptureExchange.getRequest().getFullBody();
            String responseBody = bodyCaptureExchange.getResponse().getFullBody();
            String endpoint = exchange.getRequest().getURI().toString();
            int statusCode = exchange.getResponse().getStatusCode().value();

            service.save(LogCallHistoryDto.builder()
                    .timestamp(LocalDateTime.now())
                    .request(requestBody)
                    .response(responseBody)
                    .endpoint(endpoint)
                    .statusCode(statusCode)
                    .build()).subscribe();

        });
    }
}
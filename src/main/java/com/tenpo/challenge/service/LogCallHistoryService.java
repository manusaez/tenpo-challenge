package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LogCallHistoryService {
    Flux<LogCallHistoryDto> getAll(Pageable pageable);

    Mono<LogCallHistoryDto> save(LogCallHistoryDto logCallHistoryDto);
}

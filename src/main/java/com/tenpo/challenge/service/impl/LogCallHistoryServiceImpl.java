package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import com.tenpo.challenge.repository.LogCallHistoryRepository;
import com.tenpo.challenge.service.LogCallHistoryService;
import com.tenpo.challenge.service.mapper.LogCallHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogCallHistoryServiceImpl implements LogCallHistoryService {

    private final LogCallHistoryRepository logCallHistoryRepository;
    private final LogCallHistoryMapper mapper;

    @Override
    public Flux<LogCallHistoryDto> getAll(Pageable pageable) {
        log.info("Fetching all the API calls from database....");
        return logCallHistoryRepository.findAllBy(pageable).map(mapper::toDto);
    }

    @Override
    public Mono<LogCallHistoryDto> save(LogCallHistoryDto logCallHistoryDto) {
        log.info("Saving call to database....");
        return logCallHistoryRepository.save(mapper.toEntity(logCallHistoryDto)).map(mapper::toDto);
    }
}

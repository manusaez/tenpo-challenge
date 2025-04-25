package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.LogCallHistoryDto;
import com.tenpo.challenge.entity.LogCallHistoryEntity;
import com.tenpo.challenge.repository.LogCallHistoryRepository;
import com.tenpo.challenge.service.impl.LogCallHistoryServiceImpl;
import com.tenpo.challenge.service.mapper.LogCallHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogCallHistoryServiceTest {

    private LogCallHistoryRepository logCallHistoryRepository;
    private LogCallHistoryMapper mapper;
    private LogCallHistoryServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        logCallHistoryRepository = mock(LogCallHistoryRepository.class);
        mapper = LogCallHistoryMapper.INSTANCE;
        service = new LogCallHistoryServiceImpl(logCallHistoryRepository, mapper);
    }

    @Test
    void logCallHistoryService_findAllBy() {
        LogCallHistoryEntity entity1 = new LogCallHistoryEntity();
        LogCallHistoryEntity entity2 = new LogCallHistoryEntity();

        Pageable pageable = PageRequest.of(0, 10);
        when(logCallHistoryRepository.findAllBy(pageable)).thenReturn(Flux.just(entity1, entity2));

        Flux<LogCallHistoryDto> result = service.getAll(pageable);

        StepVerifier.create(result)
                .expectNext(mapper.toDto(entity1))
                .expectNext(mapper.toDto(entity2))
                .verifyComplete();

    }

    @Test
    void logCallHistoryService_save() {
        LogCallHistoryDto dto = new LogCallHistoryDto();
        LogCallHistoryEntity entity1 = new LogCallHistoryEntity();
        LogCallHistoryEntity entity2 = new LogCallHistoryEntity();

        when(logCallHistoryRepository.save(entity1)).thenReturn(Mono.just(entity2));

        Mono<LogCallHistoryDto> result = service.save(dto);

        StepVerifier.create(result)
                .expectNext(mapper.toDto(entity1))
                .verifyComplete();

    }

}

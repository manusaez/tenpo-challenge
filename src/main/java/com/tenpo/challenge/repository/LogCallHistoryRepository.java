package com.tenpo.challenge.repository;

import com.tenpo.challenge.entity.LogCallHistoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface LogCallHistoryRepository extends ReactiveCrudRepository<LogCallHistoryEntity, Long> {

    Flux<LogCallHistoryEntity> findAllBy(Pageable pageable);
}

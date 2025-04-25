package com.tenpo.challenge.client.impl;

import com.tenpo.challenge.client.PercentageApiClient;
import com.tenpo.challenge.exception.MaxRetriesException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PercentageApiClientImpl implements PercentageApiClient {

    private final WebClient webClient;

    public PercentageApiClientImpl(WebClient.Builder webClientBuilder,
                                   @Value("${endpoint.random-number-api}") String uri) {
        this.webClient = webClientBuilder.baseUrl(uri).build();
    }

    @Cacheable(value = "percentage-cache")
    public Mono<Integer[]> getPercentage() {
        return this.getFromApi();
    }

    @Retry(name = "retryPercentage", fallbackMethod = "fallbackRetry")
    private Mono<Integer[]> getFromApi() {
        log.info("Trying to get the percentage from the external service...");
        return this.webClient.get()
                .retrieve()
                .bodyToMono(Integer[].class);
    }

    public Mono<Integer[]> fallbackRetry(Exception ex){
        return Mono.error(new MaxRetriesException("Percentage service failed to process after 3 retries " +
                "and there is no cached data."));
    }

}

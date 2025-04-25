package com.tenpo.challenge.client;

import reactor.core.publisher.Mono;

public interface PercentageApiClient {

    Mono<Integer[]> getPercentage();
}

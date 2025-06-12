package com.vukkumsp.notes_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AuthRepository extends ReactiveCrudRepository<Auth, Long> {
    Mono<Auth> findByUsername(String username);
}

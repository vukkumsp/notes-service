package com.vukkumsp.notes_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
}

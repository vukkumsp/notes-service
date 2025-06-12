package com.vukkumsp.notes_service.service;

import com.vukkumsp.notes_service.repository.Note;
import com.vukkumsp.notes_service.repository.NoteRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NoteService {

    private final NoteRepository noteRepo;

    public NoteService(NoteRepository noteRepo){
        this.noteRepo = noteRepo;
    }

    public Flux<Note> getAllNotes(){
        return this.noteRepo.findAll();
    }

    @Cacheable(value = "notes", key = "#id")
    public Mono<Note> getNote(Long id){
        return this.noteRepo.findById(id);
    }

    @CachePut(value = "notes", key = "#note.id")
    public Mono<Note> updateNote(Note note){
        return noteRepo.save(note);
    }

    public Mono<Note> addNote(Note note){
        return noteRepo.save(note);
    }

    @CacheEvict(value = "notes", key = "#id")
    public Mono<Void> deleteNote(Long id){
        return noteRepo.deleteById(id);
    }
}

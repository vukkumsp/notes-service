package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.repository.Note;
import com.vukkumsp.notes_service.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteRepository noteRepo;

    public NoteController(NoteRepository noteRepo){
        this.noteRepo = noteRepo;
    }

    @GetMapping("/notes")
    public Flux<Note> getAllNotes(){
        return noteRepo.findAll();
    }

    @GetMapping("/notes/{id}")
    public Mono<Note> getNote(@PathVariable Long id){
        return noteRepo.findById(id);
    }

    @PostMapping("/notes")
    public Boolean postNote(){
        return true;
    }
}

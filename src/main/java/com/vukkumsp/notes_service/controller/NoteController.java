package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.repository.Note;
import com.vukkumsp.notes_service.repository.NoteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteRepository noteRepo;

    public NoteController(NoteRepository noteRepo){
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public Flux<Note> getAllNotes(){
        return noteRepo.findAll();
    }
}

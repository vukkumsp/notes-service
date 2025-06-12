package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.repository.Note;
import com.vukkumsp.notes_service.repository.NoteRepository;
import com.vukkumsp.notes_service.service.NoteService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService NoteService){
        this.noteService = noteService;
    }

    @GetMapping("/notes")
    public Flux<Note> getAllNotes(){
        return this.noteService.getAllNotes();
    }

    @GetMapping("/notes/{id}")
    public Mono<Note> getNote(@PathVariable Long id){
        return this.noteService.getNote(id);
    }

    @PostMapping("/notes")
    public Mono<Note> updateNote(@RequestBody Note note){
        return this.noteService.updateNote(note);
    }

    @PutMapping("/notes")
    public Mono<Note> addNote(@RequestBody Note note){
        return this.noteService.addNote(note);
    }
}

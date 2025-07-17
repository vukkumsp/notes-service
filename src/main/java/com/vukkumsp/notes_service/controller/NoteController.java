package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.repository.Note;
import com.vukkumsp.notes_service.repository.NoteRepository;
import com.vukkumsp.notes_service.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public Flux<Note> getAllNotes(){
        return this.noteService.getAllNotes();
    }

    @GetMapping("/notes/{id}")
    public Mono<Note> getNote(@PathVariable Long id){
        return this.noteService.getNote(id);
    }

    @PutMapping("/notes")
    public Mono<Note> updateNote(@RequestBody Note note){
        return this.noteService.updateNote(note);
    }

    @PostMapping("/notes")
    public Mono<Note> addNote(@RequestBody Note note){
        return this.noteService.addNote(note);
    }

    @DeleteMapping("/notes/{id}")
    public Mono<ResponseEntity<?>> deleteNote(@PathVariable Long id){
        return this.noteService.deleteNote(id).then(Mono.just(ResponseEntity.ok().build()));
    }
}

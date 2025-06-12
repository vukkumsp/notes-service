package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.repository.Auth;
import com.vukkumsp.notes_service.repository.AuthRepository;
import com.vukkumsp.notes_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    AuthRepository userRepo;

    @GetMapping("/login")
    public Mono<Auth> getLogin(){
        return userRepo.findByUsername("admin");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody LoginDTO loginRequest){
        return userRepo.findByUsername(loginRequest.getUsername())
                .map(auth -> {
                    String token = JwtUtil.generateToken(loginRequest.getUsername(), "ADMIN");
                    Long tokenDuration = JwtUtil.getDuration();
                    Map<String, String> tokenObject = new HashMap<>();
                    tokenObject.put("token", token);
                    tokenObject.put("duration", tokenDuration.toString());
                    return ResponseEntity.ok(tokenObject);
                })
                .switchIfEmpty(
                        Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                .build())
                );
    }
}

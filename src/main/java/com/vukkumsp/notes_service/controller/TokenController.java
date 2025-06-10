package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @GetMapping("/guest")
    public ResponseEntity<?> generateGuestToken(){
        String token = JwtUtil.generateToken("guest_user", "GUEST");
        Long tokenDuration = JwtUtil.getDuration();
        Map<String, String> tokenObject = new HashMap<>();
        tokenObject.put("token", token);
        tokenObject.put("duration", tokenDuration.toString());
        return ResponseEntity.ok(tokenObject);
    }
}

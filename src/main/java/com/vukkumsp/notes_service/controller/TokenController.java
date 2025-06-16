package com.vukkumsp.notes_service.controller;

import com.vukkumsp.notes_service.security.JwtUtil;
import com.vukkumsp.notes_service.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JwtService jwt;

    public TokenController(JwtService jwt){
        this.jwt = jwt;
    }

    @GetMapping("/guest")
    public ResponseEntity<?> generateGuestToken(){
        String token = JwtUtil.generateToken("guest_user", "GUEST");
        Long tokenDuration = JwtUtil.getDuration();
        Map<String, String> tokenObject = new HashMap<>();
        tokenObject.put("token", token);
        tokenObject.put("duration", tokenDuration.toString());
        return ResponseEntity.ok(tokenObject);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyToken(@RequestBody AuthRequest authRequest){
        HashMap<String, String> response = new HashMap<>();
        Jwt token = jwt.validateToken(authRequest.getToken());
        response.put("username",token.getSubject());
        response.put("token",token.toString());

        return ResponseEntity.ok(response);
    }
}

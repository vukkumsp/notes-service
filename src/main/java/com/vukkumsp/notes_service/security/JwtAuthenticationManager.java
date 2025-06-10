//package com.vukkumsp.notes_service.security;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//
//// @Component
//public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
//
//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) {
//        String token = authentication.getCredentials().toString();
//
//        try {
//            Claims claims = JwtUtil.validateToken(token);
//            String username = claims.getSubject();
//
//            if (username != null) {
//                return Mono.just(new UsernamePasswordAuthenticationToken(
//                        username,
//                        null,
//                        Collections.emptyList() // optionally map roles from claims
//                ));
//            } else {
//                return Mono.empty();
//            }
//        } catch (JwtException e) {
//            return Mono.empty(); // token invalid or expired
//        }
//    }
//}

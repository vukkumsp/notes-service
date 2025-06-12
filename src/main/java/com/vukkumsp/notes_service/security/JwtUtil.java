package com.vukkumsp.notes_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Secret key (for demo, you can move this to `application.properties` or env)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token duration
    private static final long duration = 3600000;

    // Generate JWT Token
    public static String generateToken(String subject, String role) {
        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + duration; // 1 hour

        System.out.println("expirationMillis : "+expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expirationMillis))
                .signWith(key)
                .compact();
    }

    public static long getDuration(){
        return duration;
    }

    // Validate and parse the JWT token
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Validate Guest token
    public static boolean isGuestTokenValid(String guestToken){
        Claims claims = getClaimsFromToken(guestToken);
        String subject = claims.getSubject();
        String role = claims.get("role", String.class);
        return subject.equalsIgnoreCase("quest_user") && role.equalsIgnoreCase("GUEST");
    }

    //Validate Guest token
    public static boolean isTokenValid(String subject, String role, String jwtToken){
        Claims claims = getClaimsFromToken(jwtToken);
        String claimSubject = claims.getSubject();
        String claimRole = claims.get("role", String.class);
        return claimSubject.equalsIgnoreCase(subject) &&
                claimRole.equalsIgnoreCase(role);
    }
}

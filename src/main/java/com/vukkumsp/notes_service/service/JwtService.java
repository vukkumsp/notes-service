package com.vukkumsp.notes_service.service;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Autowired
    public JwtService(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        // Setup encoder
        JWK jwk = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();
        JWKSource<SecurityContext> jwkSource = (jwkSelector, context) -> jwkSelector.select(new JWKSet(jwk));
        this.encoder = new NimbusJwtEncoder(jwkSource);

        // Setup decoder
        this.decoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    public String generateToken(String username, Map<String, Object> additionalClaims) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("your-app-name")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .claims(claimsMap -> {
                    if(additionalClaims != null) {
                        claimsMap.putAll(additionalClaims);
                    }
                })
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt validateToken(String token) {
        return decoder.decode(token); // throws exception if invalid or expired
    }

    public String getUsernameFromToken(String token) {
        return validateToken(token).getSubject();
    }
}


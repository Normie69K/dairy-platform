package com.dairy.backend.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final long EXPIRY_MS = 24 * 60 * 60 * 1000; // 24h
    private final SecretKey key;

    public JwtService() {
        String secret = System.getenv("JWT_SECRET");
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET missing or too short");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(UUID userId, String phone, String role) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("phone", phone)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(EXPIRY_MS)))
                .signWith(key)
                .compact();
    }
}

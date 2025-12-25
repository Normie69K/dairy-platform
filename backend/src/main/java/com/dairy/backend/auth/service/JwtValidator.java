package com.dairy.backend.auth.service;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.security.Key;

@Service
public class JwtValidator {

    private final Key key;

    public JwtValidator(@Value("${JWT_SECRET}") String secret) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET missing or too short");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Key getKey() {
        return key;
    }
}

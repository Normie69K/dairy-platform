package com.dairy.backend.user.dto;

import com.dairy.backend.user.model.Role;

import java.time.Instant;
import java.util.UUID;

public class UserResponseDto {

    private UUID id;
    private String phone;
    private Role role;
    private boolean verified;
    private Instant createdAt;

    public UserResponseDto(
            UUID id,
            String phone,
            Role role,
            boolean verified,
            Instant createdAt
    ) {
        this.id = id;
        this.phone = phone;
        this.role = role;
        this.verified = verified;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public boolean isVerified() {
        return verified;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

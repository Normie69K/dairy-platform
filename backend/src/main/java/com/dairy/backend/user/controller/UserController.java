package com.dairy.backend.user.controller;

import com.dairy.backend.user.dto.UpdateUserDto;
import com.dairy.backend.user.dto.UserResponseDto;
import com.dairy.backend.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> me(Authentication authentication) {

        UUID userId = UUID.fromString(authentication.getName());

        return ResponseEntity.ok(
                userService.getUserProfile(userId)
        );
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> updateMe(
            Authentication authentication,
            @RequestBody(required = false) UpdateUserDto body
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        // placeholder update
        return ResponseEntity.ok(
                userService.getUserProfile(userId)
        );
    }
}

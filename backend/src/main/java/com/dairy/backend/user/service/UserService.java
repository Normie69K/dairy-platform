package com.dairy.backend.user.service;

import com.dairy.backend.user.dto.UserResponseDto;
import com.dairy.backend.user.model.User;
import com.dairy.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getUserProfile(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponseDto(
                user.getId(),
                user.getPhone(),
                user.getRole(),
                user.isVerified(),
                user.getCreatedAt()
        );
    }
}

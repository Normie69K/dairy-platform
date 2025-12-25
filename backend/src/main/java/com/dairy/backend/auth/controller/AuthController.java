package com.dairy.backend.auth.controller;

import com.dairy.backend.auth.dto.JwtResponseDto;
import com.dairy.backend.auth.dto.OtpRequestDto;
import com.dairy.backend.auth.dto.OtpVerifyDto;
import com.dairy.backend.auth.service.JwtService;
import com.dairy.backend.auth.service.OtpService;
import com.dairy.backend.user.model.Role;
import com.dairy.backend.user.model.User;
import com.dairy.backend.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(
            OtpService otpService,
            UserRepository userRepository,
            JwtService jwtService
    ) {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpRequestDto request) {

        String phone = request.getPhone();

        if (phone == null || phone.length() < 10) {
            return ResponseEntity.badRequest().build();
        }

        String otp = otpService.generateOtp(phone);

        // TEMP: expose OTP for local testing only
        //return ResponseEntity.ok(Map.of("otp", otp));
        return ResponseEntity.ok().build();
    }



    @PostMapping("/verify-otp")
    public ResponseEntity<JwtResponseDto> verifyOtp(@RequestBody OtpVerifyDto request) {

        String phone = request.getPhone();
        String otp = request.getOtp();

        if (phone == null || otp == null) {
            return ResponseEntity.badRequest().build();
        }

        String storedOtp = otpService.getOtp(phone);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findByPhone(phone).orElse(null);

        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setRole(Role.CUSTOMER);
            user.setVerified(true);
            userRepository.save(user);
        } else if (!user.isVerified()) {
            user.setVerified(true);
            userRepository.save(user);
        }

        otpService.deleteOtp(phone);

        String jwt = jwtService.generate(
                user.getId(),
                user.getPhone(),
                user.getRole().name()
        );

        return ResponseEntity.ok(new JwtResponseDto(jwt));
    }
}

package com.dairy.backend.auth.controller;

import com.dairy.backend.auth.dto.JwtResponseDto;
import com.dairy.backend.auth.dto.OtpVerifyDto;
import com.dairy.backend.auth.dto.OtpRequestDto;
import com.dairy.backend.auth.service.OtpService;
import com.dairy.backend.auth.service.JwtService;
import com.dairy.backend.user.model.User;
import com.dairy.backend.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(OtpService otpService,
                          UserRepository userRepository,
                          JwtService jwtService) {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/request-otp")
    public ResponseEntity<Void> requestOtp(@RequestBody OtpRequestDto request) {

        String phone = request.getPhone();

        // minimal guard, not validation
        if (phone == null || phone.length() < 10) {
            return ResponseEntity.badRequest().build();
        }

        String otp = otpService.generateOtp(phone);

        // TEMP: log OTP (remove when SMS is added)
        log.info("OTP for phone {} is {}", phone, otp);

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

        User user = userRepository.findByPhone(phone)
                .orElseGet(() -> {
                    User u = new User();
                    u.setPhone(phone);
                    u.setRole("CUSTOMER");
                    return u;
                });

        if (!user.isVerified()) {
            user.setVerified(true);
            userRepository.save(user);
        }

        otpService.deleteOtp(phone);

        String jwt = jwtService.generate(
                user.getId(),
                user.getPhone(),
                user.getRole()
        );

        return ResponseEntity.ok(new JwtResponseDto(jwt));
    }


}

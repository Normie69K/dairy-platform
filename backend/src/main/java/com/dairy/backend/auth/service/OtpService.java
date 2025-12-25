package com.dairy.backend.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {

    private static final int OTP_TTL_SECONDS = 180;
    private static final String OTP_PREFIX = "otp:";

    private final RedisTemplate<String, String> redisTemplate;
    private final Random random = new Random();

    public OtpService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateOtp(String phone) {
        String otp = String.valueOf(100000 + random.nextInt(900000));
        String key = OTP_PREFIX + phone;

        redisTemplate.opsForValue()
                .set(key, otp, Duration.ofSeconds(OTP_TTL_SECONDS));

        return otp;
    }

    public String getOtp(String phone) {
        return redisTemplate.opsForValue().get(OTP_PREFIX + phone);
    }

    public void deleteOtp(String phone) {
        redisTemplate.delete(OTP_PREFIX + phone);
    }
}
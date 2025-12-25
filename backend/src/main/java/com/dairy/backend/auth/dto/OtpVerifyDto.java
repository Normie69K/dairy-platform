package com.dairy.backend.auth.dto;


public class OtpVerifyDto {

    private String phone;
    private String otp;

    public String getPhone() {
        return phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

package com.dhatvibs.modules.dto;


import lombok.Data;

@Data
public class SendOtpResponse {
    private String phone;
    private String otpSent;
}

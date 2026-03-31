package com.dhatvibs.modules.dto;


import lombok.Data;

@Data
public class VerifyOtpApiResponse {

    private boolean success;
    private String message;
    private VerifyOtpData data;
    private AuthResponse token;
}

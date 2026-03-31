package com.dhatvibs.modules.dto;

import lombok.Data;

@Data
public class SendOtpApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}

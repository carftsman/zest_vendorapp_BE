package com.dhatvibs.modules.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String phone;
    private String newPassword;
	
}
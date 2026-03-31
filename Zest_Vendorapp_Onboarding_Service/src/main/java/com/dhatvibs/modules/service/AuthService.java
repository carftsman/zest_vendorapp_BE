package com.dhatvibs.modules.service;

import com.dhatvibs.modules.dto.AuthResponse;
import com.dhatvibs.modules.dto.SendOtpApiResponse;
import com.dhatvibs.modules.dto.SendOtpRequest;
import com.dhatvibs.modules.dto.SendOtpResponse;
import com.dhatvibs.modules.dto.VerifyOtpApiResponse;

public interface AuthService {

	SendOtpApiResponse<SendOtpResponse> sendOtp(String phone);

	VerifyOtpApiResponse verifyOtp(String phone, String otp);

    AuthResponse login(String phone);

//    String resendOtp(String phone);
//
//    String forgotPassword(String phone);

    String resetPassword(String phone, String newPassword);

	AuthResponse refreshToken(String refreshToken);
}
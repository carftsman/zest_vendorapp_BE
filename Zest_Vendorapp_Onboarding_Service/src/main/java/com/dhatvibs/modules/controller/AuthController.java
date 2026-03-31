package com.dhatvibs.modules.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.dto.*;
import com.dhatvibs.modules.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/vendor/auth")

@Tag(name = "Onbording APIs", description = "Onbording APIs for Vendor App")

public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Operation(
    	    summary = "Send OTP",
    	    description = "Sends OTP to a valid Indian phone number for login or registration"
    	)
    	@ApiResponses(value = {
    	    @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
    	    @ApiResponse(responseCode = "400", description = "Invalid phone number / Too many requests"),
    	    @ApiResponse(responseCode = "500", description = "Internal server error")
    	})

    @PostMapping("/send-otp")
    public SendOtpApiResponse<SendOtpResponse> sendOtp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Phone number (10 digits, starts with 6-9)",
                    required = true
                )
    		@RequestBody SendOtpRequest request) {
    	
        return authService.sendOtp(request.getPhone());
    }
    
    @Operation(
            summary = "Verify OTP",
            description = "Verifies OTP and returns access & refresh tokens"
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid OTP / Expired / User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        })

    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpApiResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {

        VerifyOtpApiResponse response =
                authService.verifyOtp(request.getPhone(), request.getOtp());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request.getPhone());
    }

//    @PostMapping("/resend-otp")
//    public SendOtpApiResponse<SendOtpResponse> resendOtp(@RequestBody SendOtpRequest request) {
//        return authService.resendOtp(request.getPhone());
//    }
//
//    @PostMapping("/forgot-password")
//    public SendOtpApiResponse<SendOtpResponse> forgotPassword(@RequestBody SendOtpRequest request) {
//        return authService.forgotPassword(request.getPhone());
//    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(
                request.getPhone(),
                request.getNewPassword()
        );
    }
}
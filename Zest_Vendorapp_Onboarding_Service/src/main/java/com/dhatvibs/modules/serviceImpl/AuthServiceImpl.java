package com.dhatvibs.modules.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.dto.AuthResponse;
import com.dhatvibs.modules.dto.SendOtpApiResponse;
import com.dhatvibs.modules.dto.SendOtpRequest;
import com.dhatvibs.modules.dto.SendOtpResponse;
import com.dhatvibs.modules.dto.VerifyOtpApiResponse;
import com.dhatvibs.modules.dto.VerifyOtpData;
import com.dhatvibs.modules.entity.OnboardingStage;
import com.dhatvibs.modules.entity.Owner;
import com.dhatvibs.modules.entity.Restaurant;
import com.dhatvibs.modules.repository.OwnerRepository;
import com.dhatvibs.modules.repository.RestaurantRepository;
import com.dhatvibs.modules.security.JwtUtil;
import com.dhatvibs.modules.service.AuthService;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private OwnerRepository ownerRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String STATIC_OTP = "123456";

    @Override
    public SendOtpApiResponse<SendOtpResponse> sendOtp(String phone) {

        SendOtpApiResponse<SendOtpResponse> apiResponse = new SendOtpApiResponse<>();

        try {
            //  Null / Empty check
            if (phone == null || phone.trim().isEmpty()) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Phone number is required");
                return apiResponse;
            }

            //  Normalize phone
            String normalizedPhone = phone.trim();

            //Validate format
            if (!normalizedPhone.matches("^[6-9]\\d{9}$")) {
                apiResponse.setSuccess(false);
                apiResponse.setMessage("Invalid phone number. Must be 10 digits and start with 6-9");
                return apiResponse;
            }

            //  Fetch or create user
            Owner owner = ownerRepository.findByPhone(normalizedPhone)
                    .orElseGet(() -> {
                        Owner newOwner = new Owner();
                        newOwner.setPhone(normalizedPhone);
                        newOwner.setPhoneVerified(false);
                        newOwner.setOnboardingStage(OnboardingStage.IN_PROGRESS);
                        return newOwner;
                    });

            //Prevent OTP spam
            if (owner.getOtpExpiry() != null &&
                owner.getOtpExpiry().isAfter(LocalDateTime.now().minusSeconds(30))) {

                apiResponse.setSuccess(false);
                apiResponse.setMessage("Please wait before requesting OTP again");
                return apiResponse;
            }

            // Generate OTP
            owner.setOtp(STATIC_OTP);
            owner.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

            ownerRepository.save(owner);

            //Prepare success response
            SendOtpResponse response = new SendOtpResponse();
            response.setPhone(normalizedPhone);
            response.setOtpSent("OTP sent successfully");

            apiResponse.setSuccess(true);
            apiResponse.setMessage("OTP sent successfully");
            apiResponse.setData(response);

            return apiResponse;

        } catch (Exception e) {
            // Handle unexpected errors
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Something went wrong: " + e.getMessage());
            return apiResponse;
        }
    }


    @Override
    public VerifyOtpApiResponse verifyOtp(String phone, String otp) {

        VerifyOtpApiResponse response = new VerifyOtpApiResponse();

        try {
            //  Validate input
            if (phone == null || phone.trim().isEmpty()) {
                response.setSuccess(false);
                response.setMessage("Phone number is required");
                return response;
            }

            if (otp == null || otp.trim().isEmpty()) {
                response.setSuccess(false);
                response.setMessage("OTP is required");
                return response;
            }

            String normalizedPhone = phone.trim();
            String normalizedOtp = otp.trim();

            // Validate phone format
            if (!normalizedPhone.matches("^[6-9]\\d{9}$")) {
                response.setSuccess(false);
                response.setMessage("Invalid phone number");
                return response;
            }

            //Fetch user
            Owner owner = ownerRepository.findByPhone(normalizedPhone)
                    .orElse(null);
            
            String restaurantId = null;
            
          //Fetch Restaurant
            Restaurant restaurant = restaurantRepository.findByOwnerId(owner.getId()).orElse(null);

            if (restaurant != null) {
                restaurantId = restaurant.getId().toString();
            } else {
                restaurantId = ""; // or null (your choice)
            }
            

            if (owner == null) {
                response.setSuccess(false);
                response.setMessage("User not found");
                return response;
            }

            boolean isNewUser = !Boolean.TRUE.equals(owner.getPhoneVerified());

            //OTP validation
            if (owner.getOtp() == null) {
                response.setSuccess(false);
                response.setMessage("OTP not generated. Please request OTP first");
                return response;
            }

            if (!owner.getOtp().equals(normalizedOtp)) {
                response.setSuccess(false);
                response.setMessage("Invalid OTP");
                return response;
            }

            // Expiry check
            if (owner.getOtpExpiry() == null ||
                owner.getOtpExpiry().isBefore(LocalDateTime.now())) {

                response.setSuccess(false);
                response.setMessage("OTP expired");
                return response;
            }

            // Mark verified
            owner.setPhoneVerified(true);
            owner.setOtp(null);
            owner.setOtpExpiry(null);

            // Make sure DB supports this value
            owner.setOnboardingStage(OnboardingStage.IN_PROGRESS); // safer than failing enum

            //  Generate tokens
            String accessToken = jwtUtil.generateAccessToken(owner.getId().toString() ,restaurantId);
            String refreshToken = jwtUtil.generateRefreshToken(owner.getId().toString(),restaurantId);

            owner.setRefreshToken(refreshToken);
            owner.setRefreshTokenExpiry(LocalDateTime.now().plusDays(7));

            ownerRepository.save(owner);

            //Prepare response
            VerifyOtpData data = new VerifyOtpData();
            data.setOwnerId(owner.getId().toString());
            data.setOnboardingStage(owner.getOnboardingStage().name());
            data.setNewUser(isNewUser);

            AuthResponse token = new AuthResponse(accessToken, refreshToken);

            response.setSuccess(true);
            response.setMessage("OTP verified successfully");
            response.setData(data);
            response.setToken(token);

            return response;

        } catch (Exception e) {
            // Handle unexpected errors
            response.setSuccess(false);
            response.setMessage("Something went wrong: " + e.getMessage());
            return response;
        }
    }
    

    @Override
    public AuthResponse login(String phone) {

        Owner owner = ownerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        
        
        String restaurantId = null;
        
      //Fetch Restaurant
        Restaurant restaurant = restaurantRepository.findByOwnerId(owner.getId()).orElse(null);

        if (restaurant != null) {
            restaurantId = restaurant.getId().toString();
        } else {
            restaurantId = ""; // or null (your choice)
        }

        if (!Boolean.TRUE.equals(owner.getPhoneVerified())) {
            throw new RuntimeException("Phone not verified");
        }

        String accessToken = jwtUtil.generateAccessToken(owner.getId().toString() ,restaurantId);
        String refreshToken = jwtUtil.generateRefreshToken(owner.getId().toString() ,restaurantId);

        owner.setRefreshToken(refreshToken);
        owner.setRefreshTokenExpiry(LocalDateTime.now().plusDays(7));

        ownerRepository.save(owner);

        return new AuthResponse(accessToken, refreshToken);
    }

//    @Override
//    public SendOtpApiResponse<SendOtpResponse> resendOtp(String phone) {
//        return sendOtp(phone);
//    }
//
//    @Override
//    public SendOtpApiResponse<SendOtpResponse> forgotPassword(String phone) {
//        return sendOtp(phone);
//    }

    @Override
    public String resetPassword(String phone, String newPassword) {

        Owner owner = ownerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ❗ Optional: ensure OTP verified before reset

        owner.setPasswordHash(passwordEncoder.encode(newPassword));

        ownerRepository.save(owner);

        return "Password reset successful";
    }
    
    
    @Override
    public AuthResponse refreshToken(String refreshToken) {

       // String phone = jwtUtil.extractPhone(refreshToken);
    	String phone=jwtUtil.extractOwnerId(refreshToken);

        Owner owner = ownerRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String restaurantId = null;
        
      //Fetch Restaurant
        Restaurant restaurant = restaurantRepository.findByOwnerId(owner.getId()).orElse(null);

        if (restaurant != null) {
            restaurantId = restaurant.getId().toString();
        } else {
            restaurantId = ""; // or null (your choice)
        }

        if (!refreshToken.equals(owner.getRefreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (owner.getRefreshTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        //Rotate refresh token (VERY IMPORTANT)
        String newAccessToken = jwtUtil.generateAccessToken(owner.getId().toString() ,restaurantId);
        String newRefreshToken = jwtUtil.generateRefreshToken(owner.getId().toString() ,restaurantId);

        owner.setRefreshToken(newRefreshToken);
        owner.setRefreshTokenExpiry(LocalDateTime.now().plusDays(7));

        ownerRepository.save(owner);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
package com.dhatvibs.modules.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "owners") 
@Data 
public class Owner { 

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    
    private UUID id; 
    @Column(nullable = true) 
    
    private String fullName; 
    @Column(nullable = false, unique = true) 
    
    private String phone; 
    @Column(unique = true) 

    private String email; 

    private String passwordHash; 

    private String profilePhoto; 

    private Boolean phoneVerified = false; 

    private Boolean emailVerified = false; 

    private Boolean isActive = true; 

    //  OTP Support 

    private String otp; 

    private LocalDateTime otpExpiry; 

    //  Onboarding tracking 

    @Enumerated(EnumType.STRING) 

    private OnboardingStage onboardingStage; 
    
    private String refreshToken;
    private LocalDateTime refreshTokenExpiry;
    
    private LocalDateTime createdAt; 

    private LocalDateTime updatedAt; 

    @PrePersist 
    protected void onCreate() { 

        createdAt = LocalDateTime.now(); 

    } 
    
    @PreUpdate 
    protected void onUpdate() { 

        updatedAt = LocalDateTime.now(); 

    } 

} 

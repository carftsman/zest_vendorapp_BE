package com.dhatvibs.modules.entity;

import java.time.LocalDate;
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
@Table(name = "restaurants")
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // -------- BASIC INFO --------
    @Column(nullable = false)
    private String name;

    private String phone;
    private String email;
    private String description;
    private String category;

     @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;

    @Enumerated(EnumType.STRING)
    private RestaurantStatus status = RestaurantStatus.PENDING;

    private Double rating = 0.0;
    private Integer totalReviews;

    private Boolean isActive = true;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Enumerated(EnumType.STRING)
    private OnboardingStage onboardingStage;

    // -------- ADDRESS (MERGED) --------
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String formattedAddress;

    // -------- MAP LOCATION --------
    private Double latitude;
    private Double longitude;

    // -------- FSSAI (MERGED) --------
    private String licenseNumber;
    private LocalDate expiryDate;
    private String certificateUrl;
    private Boolean fssaiVerified;

    // -------- GST (MERGED) --------
    private String gstNumber;
    private String gstCertificateUrl;
    private Boolean gstVerified;

    // -------- BANK (MERGED) --------
    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branchName;
    private String cancelledChequeUrl;
    private Boolean bankVerified;

    // -------- UPI (MERGED) --------
    private String upiId;
    private Boolean upiVerified;

    // -------- TIMESTAMPS --------
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

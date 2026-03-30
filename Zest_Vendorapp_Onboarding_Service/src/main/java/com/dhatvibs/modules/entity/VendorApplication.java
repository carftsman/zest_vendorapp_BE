package com.dhatvibs.modules.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "vendor_applications") 
@Data 
public class VendorApplication { 

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 

    private UUID id; 
    @Enumerated(EnumType.STRING) 
    private ApplicationStatus status = ApplicationStatus.PENDING; 

    private Boolean termsAccepted; 
    private String rejectionReason; 
    private UUID reviewedBy; 
    private LocalDateTime submittedAt; 
    private LocalDateTime approvedAt; 
    private LocalDateTime reviewedAt; 

    @OneToOne 
    @JoinColumn(name = "restaurant_id") 
    private Restaurant restaurant; 

}

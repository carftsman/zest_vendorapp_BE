package com.dhatvibs.modules.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "delivery_areas") 
@Data 
public class DeliveryArea { 

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 

    private UUID id; 
    @Column(nullable = false) 

    private Double latitude; 
    @Column(nullable = false) 

    private Double longitude; 
    @Column(nullable = false) 

    private Integer deliveryRadiusKm; 
    @Column(nullable = false) 

    private Double deliveryFee; 
    @Column(nullable = false) 

    private Double minOrderAmount; 

    @ManyToOne(optional = false) 

    @JoinColumn(name = "restaurant_id", nullable = false) 

    private Restaurant restaurant; 

    @CreationTimestamp 

    private LocalDateTime createdAt; 

    @UpdateTimestamp 

    private LocalDateTime updatedAt; 

}

package com.dhatvibs.modules.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "pan_cards") 
@Data 

public class PanCard { 

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 
    
    private UUID id; 
    private String panNumber; 

    private String panImageUrl; 
    private Boolean verified = false; 

    @ManyToOne 
    @JoinColumn(name = "owner_id") 

    private Owner owner; 

}

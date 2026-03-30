package com.dhatvibs.modules.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "cuisines") 
@Data 

public class Cuisine { 

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID) 

    private UUID id; 
    @Column(unique = true) 

    private String name; 

} 
package com.dhatvibs.modules.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity 
@Table( 
name = "operating_hours", 
uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "day"}) // Prevent duplicate day per restaurant 

) 
@Data 
public class OperatingHours { 

      @Id 
      @GeneratedValue(strategy = GenerationType.UUID) 

      private UUID id; 

 

      @Enumerated(EnumType.STRING)  

      private DayOfWeek day; // Use enum instead of String (prevents invalid values like "MONDAAAAY") 

      private Boolean isOpen = true; // Default open 

      private LocalTime openTime; // Should be NULL when isOpen = false 

      private LocalTime closeTime; // Should be NULL when isOpen = false 

 
      @ManyToOne 
      @JoinColumn(name = "restaurant_id", nullable = false) // Mandatory relationship 

      private Restaurant restaurant; 

      private LocalDateTime createdAt = LocalDateTime.now(); //Auto set at creation 

      private LocalDateTime updatedAt; //Will be updated automatically 

      @PreUpdate 

       public void preUpdate() { 

       this.updatedAt = LocalDateTime.now(); // Auto update timestamp before DB update 

      } 

   } 

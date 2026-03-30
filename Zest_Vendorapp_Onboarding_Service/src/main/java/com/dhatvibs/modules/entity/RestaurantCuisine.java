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
import jakarta.persistence.UniqueConstraint;

@Entity 
@Table( 

    name = "restaurant_cuisines", 

    uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "cuisine_id"})  //Prevent duplicate cuisine per restaurant 

) 

@Data 
public class RestaurantCuisine { 

    @Id 

    @GeneratedValue(strategy = GenerationType.UUID) 

    private UUID id; 

    @ManyToOne 

    @JoinColumn(name = "restaurant_id", nullable = false) // Ensure always linked to a restaurant 

    private Restaurant restaurant; 

    @ManyToOne 

    @JoinColumn(name = "cuisine_id", nullable = false) //Ensure always linked to a cuisine 

    private Cuisine cuisine; 

}

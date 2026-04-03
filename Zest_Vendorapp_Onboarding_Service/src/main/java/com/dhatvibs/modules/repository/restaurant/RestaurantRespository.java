package com.dhatvibs.modules.repository.restaurant;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.modules.entity.Restaurant;

public interface RestaurantRespository extends JpaRepository<Restaurant, UUID> {
	
	Optional<Restaurant> findByOwnerId(UUID ownerId);
}
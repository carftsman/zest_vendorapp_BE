package com.dhatvibs.modules.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhatvibs.modules.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    //Get single restaurant (if only one per owner)
    Optional<Restaurant> findByOwnerId(UUID ownerId);

    //If owner can have multiple restaurants
    List<Restaurant> findAllByOwnerId(UUID ownerId);

    // check if restaurant exists for owner
    boolean existsByOwnerId(UUID ownerId);
}
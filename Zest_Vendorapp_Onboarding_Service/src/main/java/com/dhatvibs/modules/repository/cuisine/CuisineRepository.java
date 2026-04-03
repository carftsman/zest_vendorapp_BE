package com.dhatvibs.modules.repository.cuisine;

import com.dhatvibs.modules.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CuisineRepository extends JpaRepository<Cuisine, UUID> {

    List<Cuisine> findByIdIn(List<UUID> ids);
}
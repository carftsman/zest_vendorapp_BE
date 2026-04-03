package com.dhatvibs.modules.repository.operatinghours;


import com.dhatvibs.modules.entity.OperatingHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.UUID;

public interface OperatingHoursRepository extends JpaRepository<OperatingHours, UUID> {

    Optional<OperatingHours> findByRestaurantIdAndDay(UUID restaurantId, DayOfWeek day);
}
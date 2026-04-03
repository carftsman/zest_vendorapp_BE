package com.dhatvibs.modules.dto.restaurants;

import java.util.UUID;

import com.dhatvibs.modules.entity.OnboardingStage;
import com.dhatvibs.modules.entity.RestaurantStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantResponseDto {

    private UUID restaurantId;
    private RestaurantStatus status;
    private OnboardingStage onboardingStage;
}

package com.dhatvibs.modules.dto.restaurants;

import com.dhatvibs.modules.entity.OnboardingStage;
import com.dhatvibs.modules.entity.RestaurantType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RestaurantTypeResponseDto {

    private UUID restaurantId;
    private RestaurantType restaurantType;
    private OnboardingStage onboardingStage;
    private String nextStep;
}
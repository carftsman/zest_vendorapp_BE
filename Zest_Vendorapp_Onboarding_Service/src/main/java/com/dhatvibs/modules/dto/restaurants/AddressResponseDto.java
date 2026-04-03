package com.dhatvibs.modules.dto.restaurants;

import com.dhatvibs.modules.entity.OnboardingStage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDto {

    private OnboardingStage onboardingStage;
}
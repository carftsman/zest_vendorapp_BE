package com.dhatvibs.modules.dto.restaurants;

import com.dhatvibs.modules.entity.OnboardingStage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CuisineResponseDto {

    private List<String> selectedCuisines;
    private OnboardingStage onboardingStage;
}
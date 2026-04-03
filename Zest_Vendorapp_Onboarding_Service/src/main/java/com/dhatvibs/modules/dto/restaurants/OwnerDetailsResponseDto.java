package com.dhatvibs.modules.dto.restaurants;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerDetailsResponseDto {

	private String ownerId;
	private String OnboardingStage;
}

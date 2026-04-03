package com.dhatvibs.modules.service.restaurant;

import org.springframework.web.multipart.MultipartFile;

import com.dhatvibs.modules.dto.restaurants.OwnerDetailsRequestDto;
import com.dhatvibs.modules.dto.restaurants.OwnerDetailsResponseDto;

public interface OwnerService {

    OwnerDetailsResponseDto saveOwnerDetails(String ownerId, OwnerDetailsRequestDto request,MultipartFile file);
}
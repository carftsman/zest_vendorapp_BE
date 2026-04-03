package com.dhatvibs.modules.dto.restaurants;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CuisineRequestDto {

    private List<UUID> cuisineIds;
}
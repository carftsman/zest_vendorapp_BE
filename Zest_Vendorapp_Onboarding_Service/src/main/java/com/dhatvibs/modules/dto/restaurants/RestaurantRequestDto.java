package com.dhatvibs.modules.dto.restaurants;


import com.dhatvibs.modules.entity.RestaurantType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRequestDto {

    @NotBlank
    private String name;

    private String phone;

    @Email
    private String email;

    private String description;

    private String category;

    @NotNull
    private RestaurantType restaurantType;
}

package com.dhatvibs.modules.dto.restaurants;

import lombok.Data;

@Data
public class DeliveryAreaRequestDto {

    private Double deliveryRadiusKm;
    private Double deliveryFee;
    private Double minOrderAmount;
}
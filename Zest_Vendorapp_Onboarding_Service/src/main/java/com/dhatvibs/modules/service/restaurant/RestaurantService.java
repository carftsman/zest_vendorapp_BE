package com.dhatvibs.modules.service.restaurant;

import java.util.UUID;

import com.dhatvibs.modules.dto.restaurants.AddressRequestDto;
import com.dhatvibs.modules.dto.restaurants.AddressResponseDto;
import com.dhatvibs.modules.dto.restaurants.CuisineRequestDto;
import com.dhatvibs.modules.dto.restaurants.CuisineResponseDto;
import com.dhatvibs.modules.dto.restaurants.DeliveryAreaRequestDto;
import com.dhatvibs.modules.dto.restaurants.DeliveryAreaResponseDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHoursRequestDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHoursResponseDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantRequestDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantResponseDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantTypeRequestDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantTypeResponseDto;

public interface RestaurantService {

    RestaurantResponseDto createRestaurant(String ownerId, RestaurantRequestDto request);
    
    AddressResponseDto saveAddress( String ownerId, AddressRequestDto request);
    
    DeliveryAreaResponseDto saveDeliveryArea(String ownerId, DeliveryAreaRequestDto request);
    
    CuisineResponseDto saveCuisines(String ownerId, CuisineRequestDto request); 
    
    OperatingHoursResponseDto saveOperatingHours(String ownerId, OperatingHoursRequestDto request);
    
    RestaurantTypeResponseDto  saveRestaurantType(String ownerId, RestaurantTypeRequestDto request);

}
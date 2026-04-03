
package com.dhatvibs.modules.controller.restaurant;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dhatvibs.modules.dto.restaurants.AddressRequestDto;
import com.dhatvibs.modules.dto.restaurants.AddressResponseDto;
import com.dhatvibs.modules.dto.restaurants.CommonApiResponse;
import com.dhatvibs.modules.dto.restaurants.CuisineRequestDto;
import com.dhatvibs.modules.dto.restaurants.CuisineResponseDto;
import com.dhatvibs.modules.dto.restaurants.DeliveryAreaRequestDto;
import com.dhatvibs.modules.dto.restaurants.DeliveryAreaResponseDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHoursRequestDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHoursResponseDto;
import com.dhatvibs.modules.dto.restaurants.OwnerDetailsRequestDto;
import com.dhatvibs.modules.dto.restaurants.OwnerDetailsResponseDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantRequestDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantResponseDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantTypeRequestDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantTypeResponseDto;
import com.dhatvibs.modules.service.restaurant.OwnerService;
import com.dhatvibs.modules.service.restaurant.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendor/onboarding")
@RequiredArgsConstructor
public class RestaurantController {

    private final OwnerService ownerService;
    private final ObjectMapper objectMapper; 
    private final RestaurantService restaurantService;

   // @PostMapping(value = "/owner-details", consumes = "multipart/form-data")
    @PostMapping(
            value = "/owner-details",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CommonApiResponse<OwnerDetailsResponseDto>> saveOwnerDetails(
            @RequestPart("data") String data,
            @RequestPart("file") MultipartFile file) throws Exception {

        // Convert JSON string → DTO
        OwnerDetailsRequestDto request =
                objectMapper.readValue(data, OwnerDetailsRequestDto.class);

        //  ownerId from JWT
        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        OwnerDetailsResponseDto response =
                ownerService.saveOwnerDetails(ownerId, request, file);

        return ResponseEntity.ok(
                CommonApiResponse.<OwnerDetailsResponseDto>builder()
                        .success(true)
                        .message("Owner details saved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    } 
    
    @PostMapping("/restaurants")
    public ResponseEntity<CommonApiResponse<RestaurantResponseDto>> createRestaurant(
            @RequestBody RestaurantRequestDto request) {

        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        RestaurantResponseDto response =
                restaurantService.createRestaurant(ownerId, request);

        return ResponseEntity.ok(
                CommonApiResponse.<RestaurantResponseDto>builder()
                        .success(true)
                        .message("Restaurant created")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    
    @PostMapping("/restaurants/address")
    public ResponseEntity<CommonApiResponse<AddressResponseDto>> saveAddress(
            @RequestBody AddressRequestDto request) {

        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        AddressResponseDto response =
                restaurantService.saveAddress(ownerId, request);

        return ResponseEntity.ok(
                CommonApiResponse.<AddressResponseDto>builder()
                        .success(true)
                        .message("Address saved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    } 
    
    @PostMapping("/restaurants/delivery-area")
    public ResponseEntity<CommonApiResponse<DeliveryAreaResponseDto>> saveDeliveryArea(
            @RequestBody DeliveryAreaRequestDto request) {

        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        DeliveryAreaResponseDto response =
                restaurantService.saveDeliveryArea(ownerId, request);

        return ResponseEntity.ok(
                CommonApiResponse.<DeliveryAreaResponseDto>builder()
                        .success(true)
                        .message("Delivery area saved")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    } 
    
    @PostMapping("/restaurants/cuisines")
    public ResponseEntity<CommonApiResponse<CuisineResponseDto>> saveCuisines(
            @RequestBody CuisineRequestDto request) {

        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        CuisineResponseDto response =
                restaurantService.saveCuisines(ownerId, request);

        return ResponseEntity.ok(
                CommonApiResponse.<CuisineResponseDto>builder()
                        .success(true)
                        .message("Cuisines saved")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    } 
    
    @PostMapping("/restaurants/operating-hours")
    public ResponseEntity<CommonApiResponse<OperatingHoursResponseDto>> saveOperatingHours(
            @RequestBody OperatingHoursRequestDto request) {

        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        OperatingHoursResponseDto response =
                restaurantService.saveOperatingHours(ownerId, request);

        return ResponseEntity.ok(
                CommonApiResponse.<OperatingHoursResponseDto>builder()
                        .success(true)
                        .message("Operating hours saved successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
    
    @PostMapping("/restaurants/type")
    public ResponseEntity<CommonApiResponse<RestaurantTypeResponseDto>> saveRestaurantType(
            @RequestBody RestaurantTypeRequestDto request) {

        String ownerId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        RestaurantTypeResponseDto response =
                restaurantService.saveRestaurantType(ownerId, request);

        return ResponseEntity.ok(
                CommonApiResponse.<RestaurantTypeResponseDto>builder()
                        .success(true)
                        .message("Restaurant type selected successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
} 
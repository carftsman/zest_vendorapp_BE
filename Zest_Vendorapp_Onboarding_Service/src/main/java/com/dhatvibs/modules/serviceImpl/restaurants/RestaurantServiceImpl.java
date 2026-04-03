package com.dhatvibs.modules.serviceImpl.restaurants;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.dto.restaurants.AddressRequestDto;
import com.dhatvibs.modules.dto.restaurants.AddressResponseDto;
import com.dhatvibs.modules.dto.restaurants.CuisineRequestDto;
import com.dhatvibs.modules.dto.restaurants.CuisineResponseDto;
import com.dhatvibs.modules.dto.restaurants.DeliveryAreaRequestDto;
import com.dhatvibs.modules.dto.restaurants.DeliveryAreaResponseDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHourDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHoursRequestDto;
import com.dhatvibs.modules.dto.restaurants.OperatingHoursResponseDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantRequestDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantResponseDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantTypeRequestDto;
import com.dhatvibs.modules.dto.restaurants.RestaurantTypeResponseDto;
import com.dhatvibs.modules.entity.Cuisine;
import com.dhatvibs.modules.entity.OnboardingStage;
import com.dhatvibs.modules.entity.OperatingHours;
import com.dhatvibs.modules.entity.Restaurant;
import com.dhatvibs.modules.entity.RestaurantStatus;
import com.dhatvibs.modules.repository.RestaurantRepository;
import com.dhatvibs.modules.repository.cuisine.CuisineRepository;
import com.dhatvibs.modules.repository.operatinghours.OperatingHoursRepository;
import com.dhatvibs.modules.service.restaurant.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    @Autowired
    private CuisineRepository cuisineRepository;
    @Autowired
    private OperatingHoursRepository operatingHoursRepository;

    @Override
    public RestaurantResponseDto createRestaurant(String ownerId, RestaurantRequestDto request) {

        Restaurant restaurant = new Restaurant();

       
        restaurant.setName(request.getName());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        restaurant.setDescription(request.getDescription());
        restaurant.setCategory(request.getCategory());
        restaurant.setRestaurantType(request.getRestaurantType());

        
        restaurant.setOwnerId(UUID.fromString(ownerId));

        
        restaurant.setStatus(RestaurantStatus.PENDING);
        restaurant.setOnboardingStage(OnboardingStage.RESTAURANT_CREATED);

        Restaurant saved = restaurantRepository.save(restaurant);

        return RestaurantResponseDto.builder()
                .restaurantId(saved.getId())
                .status(saved.getStatus())
                .onboardingStage(saved.getOnboardingStage())
                .build();
    } 
    
    @Override
    public AddressResponseDto saveAddress(String ownerId, AddressRequestDto request) {

        UUID ownerUUID = UUID.fromString(ownerId);

        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerUUID)
                .orElseThrow(() -> new RuntimeException("Restaurant not found for this owner"));

        
        restaurant.setAddressLine1(request.getAddressLine1());
        restaurant.setAddressLine2(request.getAddressLine2());
        restaurant.setCity(request.getCity());
        restaurant.setState(request.getState());
        restaurant.setPostalCode(request.getPostalCode());
        restaurant.setFormattedAddress(request.getFormattedAddress());

       
        restaurant.setLatitude(request.getLatitude());
        restaurant.setLongitude(request.getLongitude());

       
        restaurant.setOnboardingStage(OnboardingStage.ADDRESS_ADDED);

        restaurantRepository.save(restaurant);

        return AddressResponseDto.builder()
                .onboardingStage(OnboardingStage.ADDRESS_ADDED)
                .build();
    } 
    
    @Override
    public DeliveryAreaResponseDto saveDeliveryArea(String ownerId, DeliveryAreaRequestDto request) {

        UUID ownerUUID = UUID.fromString(ownerId);

        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerUUID)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setDeliveryRadiusKm(request.getDeliveryRadiusKm());
        restaurant.setDeliveryFee(request.getDeliveryFee());
        restaurant.setMinOrderAmount(request.getMinOrderAmount());

        restaurant.setOnboardingStage(OnboardingStage.DELIVERY_AREA_SET);

        restaurantRepository.save(restaurant);

        return DeliveryAreaResponseDto.builder()
                .onboardingStage(OnboardingStage.DELIVERY_AREA_SET)
                .build();
    } 
    
   

    @Override
    public CuisineResponseDto saveCuisines(String ownerId, CuisineRequestDto request) {

        UUID ownerUUID = UUID.fromString(ownerId);

        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerUUID)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        List<Cuisine> cuisines = cuisineRepository.findByIdIn(request.getCuisineIds());

        if (cuisines.size() != request.getCuisineIds().size()) {
            throw new RuntimeException("Some cuisine IDs are invalid");
        }

        restaurant.setCuisines(new HashSet<>(cuisines));

        restaurant.setOnboardingStage(OnboardingStage.CUISINES_SELECTED);

        restaurantRepository.save(restaurant);

        List<String> cuisineNames = cuisines.stream()
                .map(Cuisine::getName)
                .toList();

        return CuisineResponseDto.builder()
                .selectedCuisines(cuisineNames)
                .onboardingStage(OnboardingStage.CUISINES_SELECTED)
                .build();
    }  
    
    
    private void saveOrUpdateDay(Restaurant restaurant, DayOfWeek day, OperatingHourDto dto) {

        OperatingHours entity = operatingHoursRepository
                .findByRestaurantIdAndDay(restaurant.getId(), day)
                .orElse(new OperatingHours());

        entity.setRestaurant(restaurant);
        entity.setDay(day);
        entity.setIsOpen(dto.getIsOpen());

        if (Boolean.TRUE.equals(dto.getIsOpen())) {

            if (dto.getOpenTime() == null || dto.getCloseTime() == null) {
                throw new RuntimeException("Open and Close time required when restaurant is open");
            }

            entity.setOpenTime(dto.getOpenTime());
            entity.setCloseTime(dto.getCloseTime());

        } else {
            entity.setOpenTime(null);
            entity.setCloseTime(null);
        }

        operatingHoursRepository.save(entity);
    }
    @Override
    public OperatingHoursResponseDto saveOperatingHours(String ownerId, OperatingHoursRequestDto request) {

        UUID ownerUUID = UUID.fromString(ownerId);

        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerUUID)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        if (Boolean.TRUE.equals(request.getApplyToAllDays())) {

            OperatingHourDto dto = request.getHours().get(0); // take first config

            for (DayOfWeek day : DayOfWeek.values()) {

                saveOrUpdateDay(restaurant, day, dto);
            }

        } else {

            for (OperatingHourDto dto : request.getHours()) {

                saveOrUpdateDay(restaurant, dto.getDay(), dto);
            }
        }

        restaurant.setOnboardingStage(OnboardingStage.OPERATING_HOURS_SET);
        restaurantRepository.save(restaurant);

        return OperatingHoursResponseDto.builder()
                .onboardingStage(OnboardingStage.OPERATING_HOURS_SET)
                .build();
    } 
    
    @Override
    public RestaurantTypeResponseDto saveRestaurantType(String ownerId, RestaurantTypeRequestDto request) {

        UUID ownerUUID = UUID.fromString(ownerId);

        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerUUID)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setRestaurantType(request.getRestaurantType());

        restaurant.setOnboardingStage(OnboardingStage.RESTAURANT_TYPE_SELECTED);

        restaurantRepository.save(restaurant);

        return RestaurantTypeResponseDto.builder()
                .restaurantId(restaurant.getId())
                .restaurantType(restaurant.getRestaurantType())
                .onboardingStage(OnboardingStage.RESTAURANT_TYPE_SELECTED)
                .nextStep("RESTAURANT_DETAILS")
                .build();
    }
    
    
}
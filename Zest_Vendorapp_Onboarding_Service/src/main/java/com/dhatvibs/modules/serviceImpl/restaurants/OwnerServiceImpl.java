package com.dhatvibs.modules.serviceImpl.restaurants;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dhatvibs.modules.dto.restaurants.OwnerDetailsRequestDto;
import com.dhatvibs.modules.dto.restaurants.OwnerDetailsResponseDto;
import com.dhatvibs.modules.entity.Owner;
import com.dhatvibs.modules.entity.OnboardingStage;
import com.dhatvibs.modules.repository.OwnerRepository;
import com.dhatvibs.modules.service.AzureBlobService;
import com.dhatvibs.modules.service.restaurant.OwnerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AzureBlobService azureBlobService;

    @Override
    public OwnerDetailsResponseDto saveOwnerDetails(
            String ownerId,
            OwnerDetailsRequestDto request,
            MultipartFile file) {

        Owner owner = ownerRepository.findById(UUID.fromString(ownerId))
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Password and Confirm Password do not match");
        }

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is missing or empty");
        }

        owner.setFullName(request.getFullName());
        owner.setEmail(request.getEmail());
        owner.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        String imageUrl;

        try {
            imageUrl = azureBlobService.uploadFile(file, "owners");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Image upload failed: " + e.getMessage());
        }

        owner.setProfilePhoto(imageUrl);
        owner.setOnboardingStage(OnboardingStage.OWNER_REGISTERED);

        ownerRepository.save(owner);

        return OwnerDetailsResponseDto.builder()
                .ownerId(owner.getId().toString())
                .OnboardingStage(owner.getOnboardingStage().name())
                .build();
    }
}
package com.dhatvibs.modules.dto.restaurants;

import lombok.Data;

@Data
public class AddressRequestDto {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String formattedAddress;
    private Double latitude;
    private Double longitude;
}
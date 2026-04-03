package com.dhatvibs.modules.dto.restaurants;

import lombok.Data;

import java.util.List;

@Data
public class OperatingHoursRequestDto {

    private Boolean applyToAllDays;
    private List<OperatingHourDto> hours;
}
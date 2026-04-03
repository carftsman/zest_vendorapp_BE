package com.dhatvibs.modules.dto.restaurants;


import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class OperatingHourDto {

    private DayOfWeek day;
    private Boolean isOpen;
    private LocalTime openTime;
    private LocalTime closeTime;
}

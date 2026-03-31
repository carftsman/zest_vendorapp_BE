package com.dhatvibs.modules.dto;

import lombok.Data;

@Data
public class VerifyOtpData {

    private String ownerId;
    private String onboardingStage;
    private boolean isNewUser;
}

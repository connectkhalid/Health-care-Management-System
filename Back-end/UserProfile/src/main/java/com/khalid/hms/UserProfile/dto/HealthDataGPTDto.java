package com.khalid.hms.UserProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HealthDataGPTDto {
    private String userId;
    private Double heightCm;
    private Double weightKg;
    private Double bloodSugarLevel;
    private String bloodPressure;
    private String bloodGroup;
    private Integer heartRate;
    private Double bmi;
    private Integer sleepHour;
    private Boolean smoke;
}

package com.khalid.hms.UserProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendHealthDataDto {
    private String userId;
    private Double heightCm;
    private Double weightKg;
    private Double bloodSugarLevel;
    private Integer heartRate;
    private Double bmi;
    private LocalDate date;
    private Integer sleepHour;
    private Integer age;
    private String bloodPressure;
    private String bloodGroup;
    private Boolean smoke;
}

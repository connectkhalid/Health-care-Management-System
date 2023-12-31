package com.khalid.hms.UserProfile.dto.RequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDataRequestDto {
    @NotNull(message = "Please Give Height Input")
    private Double heightCm;
    @NotNull(message = "Please Give weight Input")
    private Double weightKg;
    @NotNull(message = "Please Give bloodSugarLevel Input")
    private Double bloodSugarLevel;
    @NotNull(message = "Please Give bloodPressure Input")
    private String bloodPressure;
    @NotNull(message = "Please Give bloodGroup Input")
    private String bloodGroup;
    @NotNull(message = "Please Give heartRate Input")
    private Integer heartRate;
    @NotNull(message = "Please Give sleepHour Input")
    private Integer sleepHour;
    @NotNull(message = "Please Give smoke Input")
    private Boolean smoke;
}

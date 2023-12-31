package com.khalid.hms.DoctorProfile.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponseDTO {
    @NotNull
    private String slotId;
    @NotNull
    private String doctorId;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private Boolean isAvailable;
    @NotNull
    private LocalDate slotDate;
}

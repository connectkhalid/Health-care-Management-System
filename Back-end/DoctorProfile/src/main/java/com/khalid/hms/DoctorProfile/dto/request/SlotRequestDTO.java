package com.khalid.hms.DoctorProfile.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequestDTO {
    @NotNull(message = "Start time can not be blank")
    private LocalTime startTime;
    @NotNull(message = "Total duration is needed to be specified")
    private Integer duration;
    @NotNull(message = "Mention the date of the slot request")
    private LocalDate slotDate;
}

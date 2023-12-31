package com.khalid.hms.DoctorProfile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotAvailabilityRequestDTO {
    private String doctor_id;
    private LocalDate slot_date;
}
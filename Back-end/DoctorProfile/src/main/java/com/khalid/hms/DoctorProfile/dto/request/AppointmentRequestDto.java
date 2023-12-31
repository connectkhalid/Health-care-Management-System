package com.khalid.hms.DoctorProfile.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppointmentRequestDto {
    @NotNull(message = "No Doctor Id found!")
    private String doctorId;
    @NotNull(message = "No Slot Id found!")
    private String slotId;
    @NotNull(message = "No appointmentType is mentioned!")
    private String appointmentType;
    private String conferenceLink;
    @NotNull(message = "Please Specify the time.")
    private LocalTime startTime;
    @NotNull(message = "Please Specify the date.")
    private LocalDate appointmentDate;
}

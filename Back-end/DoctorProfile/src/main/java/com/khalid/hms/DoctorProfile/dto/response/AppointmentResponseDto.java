package com.khalid.hms.DoctorProfile.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
    private String appointmentId;
    @NotNull(message = "no Patient Id found!")
    private String patientId;

    @NotNull(message = "No Patient Name found!")
    private String patientName;

    @NotNull(message = "no Doctor Id found!")
    private String doctorId;

    @NotNull(message = "No Doctor Name found!")
    private String doctorName;

    @NotNull(message = "no Slot Id found!")
    private String slotId;

    @NotNull(message = "no appointmentType is mentioned!")
    private String appointmentType;

    private String conferenceLink;
    private String status;

    @NotNull(message = "No Start Time Found")
    private LocalTime startTime;
    @NotNull(message = "No Stop Time Found")
    private LocalTime endTime;

    @NotNull(message = "Appointment date is not present!")
    private LocalDate appointmentDate;

    private LocalDateTime createdAt;
}

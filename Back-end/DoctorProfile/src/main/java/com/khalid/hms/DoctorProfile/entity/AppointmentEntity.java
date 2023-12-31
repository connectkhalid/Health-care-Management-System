package com.khalid.hms.DoctorProfile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment_table")
public class AppointmentEntity {
    @Id
    private String appointmentId;

    @NotNull(message = "no Patient Id found!")
    private String patientId;
    @NotNull(message = "no Patient name found!")
    private String patientName;

    @NotNull(message = "no Doctor Id found!")
    private String doctorId;
    @NotNull(message = "no Doctor name found!")
    private String doctorName;

    @NotNull(message = "no Slot Id found!")
    private String slotId;
    @NotNull(message = "no appointmentType is mentioned!")
    private String appointmentType;
    private String conferenceLink;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private LocalDateTime createdAt;
}

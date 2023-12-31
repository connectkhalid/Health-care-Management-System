package com.khalid.hms.DoctorProfile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReviewEntity {
    @Id
    private String reviewId;
    private String message;
    private Integer rating;
    private String doctorId;
    private String patientId;
    private String patientName;
    private LocalDateTime createdAt;
}

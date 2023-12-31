package com.khalid.hms.DoctorProfile.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {
    @NotNull(message = "message cannot be null")
    private String message;

    @NotNull(message = "Rating cannot be null")
    private Integer rating;

    @NotNull(message = "DoctorId cannot be null")
    private String doctorId;

    @NotNull(message = "PatientId cannot be null")
    private String patientId;
}

package com.khalid.hms.DoctorProfile.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    @NotNull(message = "Massage cannot be null")
    private String message;

    @NotNull(message = "Rating cannot be null")
    private Integer rating;

    @NotNull(message = "patientName cannot be null")
    private String patientName;

    @NotNull(message = "CreatedAt cannot be null")
    private LocalDateTime createdAt;
}

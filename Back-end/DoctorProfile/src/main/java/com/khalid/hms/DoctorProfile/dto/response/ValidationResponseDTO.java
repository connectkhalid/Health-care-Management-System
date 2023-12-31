package com.khalid.hms.DoctorProfile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDTO {
    private Boolean isValid;
    private String massage;
}

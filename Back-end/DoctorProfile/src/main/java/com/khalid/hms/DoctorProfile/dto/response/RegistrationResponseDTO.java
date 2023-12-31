package com.khalid.hms.DoctorProfile.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Name can not be null")
    private String name;
    private String email;
    private String image;
    private String role;
    private String gender;
    private String registration_number_BDMC;
    private String allocated_room;
    private String  qualifications;
    private Boolean isAvailable;
    private Boolean isValid;
    private LocalDate created_at;
}
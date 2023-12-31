package com.khalid.hms.DoctorProfile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {
    @NotBlank(message = "Name can not be null")
    private String name;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotBlank(message = "Password must not be blank")
    private String password;
    private String image;
    private String gender;
    private String department;
    private String registration_number_BDMC;
    private String allocated_room;
    private String qualifications;
}

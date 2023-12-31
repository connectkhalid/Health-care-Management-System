package com.khalid.hms.UserProfile.dto.ResponseDto;

import com.khalid.hms.UserProfile.validator.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String name;
    private String email;
    private String role;
    private String gender;
    private Integer age;
    private String address;
    private String image;
    private String contactInfo;
    private Boolean isVerified;
    private LocalDate createdAt;
}
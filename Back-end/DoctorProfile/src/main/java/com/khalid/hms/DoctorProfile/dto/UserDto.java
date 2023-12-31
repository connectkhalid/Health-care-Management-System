package com.khalid.hms.DoctorProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
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

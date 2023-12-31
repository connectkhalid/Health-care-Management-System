package com.khalid.hms.UserProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequestDto {
    private String name;
    private String gender;
    private Integer age;
    private String address;
    private String image;
    private String contactInfo;
}

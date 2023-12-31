package com.khalid.hms.UserProfile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String userID;
    private String name;
    private String email;
    private String gender;
    private Integer age;
    private String contactInfo;
    private String address;
    private String image;

}

package com.khalid.hms.UserProfile.dto.RequestDto;

import com.khalid.hms.UserProfile.validator.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password must not be null")
    private String password;

    @ValidRole
    private String role;

    @NotBlank(message = "Please input your name!")
    private String name;

    private String gender;
    private Integer age;
    private String contactInfo;
    private String address;
    private String image;
    private Boolean isVerified;
}
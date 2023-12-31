package com.khalid.hms.UserProfile.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestModel {
    private String email;
    private String password;
}

package com.khalid.hms.UserProfile.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseModel {
    private String username;
    private String bearerToken;
}

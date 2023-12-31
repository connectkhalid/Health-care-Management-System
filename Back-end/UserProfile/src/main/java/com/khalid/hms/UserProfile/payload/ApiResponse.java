package com.khalid.hms.UserProfile.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
        private String  message;
        private boolean success;
        private HttpStatus status;
    }


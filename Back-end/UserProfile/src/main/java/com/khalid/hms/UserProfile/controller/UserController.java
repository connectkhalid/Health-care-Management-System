package com.khalid.hms.UserProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.khalid.hms.UserProfile.dto.*;
import com.khalid.hms.UserProfile.dto.RequestDto.HealthDataRequestDto;
import com.khalid.hms.UserProfile.dto.RequestDto.UserRequestDto;
import com.khalid.hms.UserProfile.dto.ResponseDto.UserResponseDto;
import com.khalid.hms.UserProfile.exceptions.AuthenticationExceptionFound;
import com.khalid.hms.UserProfile.service.serviceImplementation.UserServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserServiceImplementation userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(
            @Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto createdUser = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PutMapping("/update-patient-profile")
    public ResponseEntity<Object> updateUserProfile(
            @RequestBody UpdateProfileRequestDto updateRequestDto) {
        UserResponseDto userProfile = userService
                .updateUserProfile(updateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userProfile);
    }
    @GetMapping("/get-patient-profile-by-token")
    public ResponseEntity<Object> getPatientProfileByToken() {
        try {
            UserResponseDto userProfile = userService.getUserProfileDataByToken();
            return ResponseEntity.status(HttpStatus.OK).body(userProfile);
        } catch (AuthenticationExceptionFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/get-patient-profile-by-id/{userId}")
    public ResponseEntity<?> getById(@PathVariable("userId") String userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getUserProfileDataById(userId));
        } catch (AuthenticationExceptionFound e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/get-patient-by-email/{email}")
    public ResponseEntity<Object> getUserByEmail(
            @PathVariable("email") String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserByEmail(email));
    }
    @PostMapping("/create-health-data")
    public ResponseEntity<Object> createHealthData(
            @RequestBody HealthDataRequestDto healthDataDto) throws JsonProcessingException {
        HealthDataDto healthData = userService.createHealthData(healthDataDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthData);
    }
    @GetMapping("/get-health-data-by-token")
    public ResponseEntity<Object> getUserHealthDataById() {
        HealthDataDto healthData = userService.getUserHealthDataById();
        return ResponseEntity.status(HttpStatus.OK).body(healthData);
    }
    @PutMapping("/update-patient-health-data")
    public ResponseEntity<Object> updateUserHealthData(
            @RequestBody HealthDataRequestDto healthDataDto) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUserHealthData(healthDataDto));
    }
    @GetMapping("/health-data")
    public ResponseEntity<Object> sendHealthData() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.sendHealthData());
    }
    @GetMapping("/get-all-health-data")
    public ResponseEntity<Object> getAllHealthData() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUserHealthData());
    }
}

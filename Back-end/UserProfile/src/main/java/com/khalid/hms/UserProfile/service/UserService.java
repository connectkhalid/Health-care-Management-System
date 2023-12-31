package com.khalid.hms.UserProfile.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.khalid.hms.UserProfile.dto.*;
import com.khalid.hms.UserProfile.dto.RequestDto.HealthDataRequestDto;
import com.khalid.hms.UserProfile.dto.RequestDto.UserRequestDto;
import com.khalid.hms.UserProfile.dto.ResponseDto.UserResponseDto;
import com.khalid.hms.UserProfile.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto user) throws Exception;
    UserDto getUser(String email);
    UserResponseDto getUserProfileDataByToken();
    UserResponseDto getUserProfileDataById(String id) throws IllegalAccessException;

    UserResponseDto updateUserProfile(UpdateProfileRequestDto updateRequestDto);
    SendHealthDataDto sendHealthData();
    HealthDataDto createHealthData(HealthDataRequestDto healthDataRequestDtoDto) throws JsonProcessingException;
    UserEntity readByEmail(String email);
    HealthDataDto getUserHealthDataById();
    HealthDataDto updateUserHealthData(HealthDataRequestDto healthDataDto) throws JsonProcessingException;
    UserResponseDto getUserByEmail(String userEmail);

    List<HealthDataDto> getAllUserHealthData();
}

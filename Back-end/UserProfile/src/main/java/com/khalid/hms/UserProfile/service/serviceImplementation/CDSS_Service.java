package com.khalid.hms.UserProfile.service.serviceImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khalid.hms.UserProfile.exceptions.ResourceNotFoundException;
import com.khalid.hms.UserProfile.dto.HealthDataGPTDto;
import com.khalid.hms.UserProfile.entity.HealthDataEntity;
import com.khalid.hms.UserProfile.entity.UserEntity;
import com.khalid.hms.UserProfile.networkmanager.UserFeignClient;
import com.khalid.hms.UserProfile.repository.HealthDataRepository;
import com.khalid.hms.UserProfile.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CDSS_Service {
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HealthDataRepository healthDataRepository;
    public void callGptWithPatientData() throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findByEmail(authentication.getName());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        String userId = user.get().getId();
        Optional<HealthDataEntity> healthDataOptional = healthDataRepository.findById(userId);
        HealthDataEntity healthDataEntity = healthDataOptional.get();
        HealthDataGPTDto patientHealthData = new ModelMapper().map(healthDataEntity, HealthDataGPTDto.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = " analyzes patient data to suggest diagnoses, treatment plans, and medication recommendations." +
                "response should be in single line for every aspect." +
                "first think properly then give the answer properly"+
                 objectMapper.writeValueAsString(patientHealthData);
         userFeignClient.chat(requestBody);

    }
}

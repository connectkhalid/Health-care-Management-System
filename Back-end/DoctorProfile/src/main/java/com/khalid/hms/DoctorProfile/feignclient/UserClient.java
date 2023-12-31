package com.khalid.hms.DoctorProfile.feignclient;

import com.khalid.hms.DoctorProfile.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", configuration = FeignClientConfiguration.class)
public interface UserClient {
    @GetMapping("/patient/get-patient-by-email/{email}")
    UserDto getUserByEmail(@PathVariable("email") String email);
    @GetMapping("/patient/get-patient-profile-by-token")
    UserDto getPatientProfileByToken();
    @GetMapping("patient/get-patient-profile-by-id/{userId}")
    UserDto getById(@PathVariable("userId") String userId);
}
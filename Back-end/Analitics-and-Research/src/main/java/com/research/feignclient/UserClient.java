package com.research.feignclient;


import com.research.dto.HealthDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "patient-service", configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface UserClient {
    @GetMapping("/patient/get-health-data-by-token")
    List<HealthDataDto> getAllUserHealthData();

}
package com.khalid.hms.UserProfile.networkmanager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "cdss-service", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {
    @PostMapping("/v1/ai/chat")
    String chat(@RequestBody String msg);

}
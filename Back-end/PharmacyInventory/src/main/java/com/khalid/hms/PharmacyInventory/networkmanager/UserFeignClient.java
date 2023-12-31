//package com.example.PharmacyInventory.networkmanager;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "user-service", configuration = FeignClientConfiguration.class)
//public interface UserFeignClient {
//    @GetMapping("/users/getUserByEmail")
//    UserDto getUserByEmail(@RequestParam("email") String email);
//
//}
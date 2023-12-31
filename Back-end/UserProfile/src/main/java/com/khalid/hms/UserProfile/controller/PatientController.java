package com.khalid.hms.UserProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.khalid.hms.UserProfile.service.serviceImplementation.CDSS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private CDSS_Service CDSSService;
    @GetMapping("/callGpt")
    public void callGptWithPatientData() {
        try {
            CDSSService.callGptWithPatientData();
        } catch (JsonProcessingException ignored) {

        }
    }
}

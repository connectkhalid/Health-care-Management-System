package com.research.controller;

import com.research.entity.ResearcherEntity;
import com.research.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/researchers")
public class ResearchController {
    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/register")
    public ResponseEntity<ResearcherEntity> createResearcher(@RequestBody ResearcherEntity researcherEntity) {
        ResearcherEntity entity= researcherService.createResearcher(researcherEntity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @GetMapping("/applied")
    public ResponseEntity<List<ResearcherEntity>> getAllResearchersForApplied() {
        List<ResearcherEntity> takenResearchers = researcherService.getAllResearchersForApplied(false);
        return new ResponseEntity<>(takenResearchers, HttpStatus.OK);
    }
    @PutMapping("/permission/{id}")
    public ResponseEntity<String> giveValidation (@PathVariable("id") Long id){
        researcherService.validation(id);
        return new ResponseEntity<>("Permission granted", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResearcherEntity>> getAllResearchers() {
        List<ResearcherEntity> researchers = researcherService.getAllResearchers();
        return new ResponseEntity<>(researchers, HttpStatus.OK);
    }

    @GetMapping("/taken")
    public ResponseEntity<List<ResearcherEntity>> getAllResearchersByIsTaken() {
        List<ResearcherEntity> takenResearchers = researcherService.getAllResearchersByIsTaken(true);
        return new ResponseEntity<>(takenResearchers, HttpStatus.OK);
    }

    @GetMapping("/notTaken")
    public ResponseEntity<List<ResearcherEntity>> getAllResearchersByIsNotTaken() {
        List<ResearcherEntity> takenResearchers = researcherService.getAllResearchersByIsNotTaken(false);
        return new ResponseEntity<>(takenResearchers, HttpStatus.OK);
    }

    //get all health datas

//    @GetMapping("/healthData")
//    public ResponseEntity<List<ResearcherEntity>> getAllHealthData() {
//        List<ResearcherEntity> researchers = researcherService.getAllResearchers();
//        return new ResponseEntity<>(researchers, HttpStatus.OK);
//    }

    @GetMapping("/getCsv/{id}")
    public ResponseEntity<?> writeUserHealthDataToCsv(@PathVariable("id") Long id) {
        String filename = "user_health_data.csv";
        researcherService.writeUserHealthDataToCsv(id, filename);

        try {
            byte[] csvData = Files.readAllBytes(Paths.get(filename));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDispositionFormData("attachment", filename);

            ByteArrayResource resource = new ByteArrayResource(csvData);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvData.length)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


}

package com.research.service;


import com.opencsv.CSVWriter;
import com.research.dto.HealthDataDto;
import com.research.entity.ResearcherEntity;
import com.research.feignclient.UserClient;
import com.research.repo.ResearcherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class ResearcherService {

    private final ResearcherRepository researcherRepository;

    @Autowired
    public ResearcherService(ResearcherRepository researcherRepository) {
        this.researcherRepository = researcherRepository;
    }
    @Autowired
    private UserClient userClient;
    public ResearcherEntity createResearcher(ResearcherEntity researcherEntity) {
        researcherEntity.setIsvalid(false);
        researcherEntity.setIsTaken(false);
        researcherRepository.save(researcherEntity);
        return researcherEntity;
    }

    public List<ResearcherEntity> getAllResearchersForApplied(boolean isTaken) {
        return researcherRepository.findByIsTaken(isTaken);
    }

    public void validation(Long id) {
        Optional<ResearcherEntity> researcher = researcherRepository.findById(id);
        if(researcher.isEmpty())
            throw new RuntimeException("Researcher is not found");
        ResearcherEntity researcherEntity= researcher.get();
        researcherEntity.setIsvalid(true);
        researcherRepository.save(researcherEntity);
    }

    public List<ResearcherEntity> getAllResearchers() {
        return researcherRepository.findAll();
    }

    public List<ResearcherEntity> getAllResearchersByIsTaken(boolean isTaken) {
        return researcherRepository.findByIsTaken(isTaken);
    }

    public List<ResearcherEntity> getAllResearchersByIsNotTaken(boolean isTaken) {
        return researcherRepository.findByIsTaken(isTaken);
    }

    public void writeUserHealthDataToCsv(Long id, String filename) {

        if(researcherRepository.findByIdAndIsvalidIsFalse(id) != null)
            throw new RuntimeException("You are not validated yet");
        List<HealthDataDto> allUserHealthData = userClient.getAllUserHealthData();

        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(new File(filename)))) {
            String[] header = {
                    "User ID", "Height (cm)", "Weight (kg)", "Blood Sugar Level",
                    "Blood Pressure", "Blood Group", "Heart Rate",
                    "BMI", "Date", "Sleep Hour", "Smoke"
            };
            csvWriter.writeNext(header);

            for (HealthDataDto userHealthDto : allUserHealthData) {
                String[] data = {
                        userHealthDto.getUserId(),
                        String.valueOf(userHealthDto.getHeightCm()),
                        String.valueOf(userHealthDto.getWeightKg()),
                        String.valueOf(userHealthDto.getBloodSugarLevel()),
                        userHealthDto.getBloodPressure(),
                        userHealthDto.getBloodGroup(),
                        String.valueOf(userHealthDto.getHeartRate()),
                        String.valueOf(userHealthDto.getBmi()),
                        String.valueOf(userHealthDto.getDate()),
                        String.valueOf(userHealthDto.getSleepHour()),
                        String.valueOf(userHealthDto.getSmoke())
                };
                csvWriter.writeNext(data);
            }

            Optional<ResearcherEntity> researcher = researcherRepository.findById(id);
            if (researcher.isEmpty())
                throw new RuntimeException("Researcher is not found");
            ResearcherEntity researcherEntity = researcher.get();
            researcherEntity.setIsTaken(true);
            researcherRepository.save(researcherEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

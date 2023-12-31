package com.khalid.hms.CDSS.controller;


import com.khalid.hms.CDSS.entity.RecommendationEntity;
import com.khalid.hms.CDSS.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomBotController {

    private final ChatGPTService chatGPTService;
    @PostMapping("/v1/ai/chat")
    public ResponseEntity<String> chat(@RequestBody String message){
        return ResponseEntity.ok(chatGPTService.chat(message));
    }

    @GetMapping("/getRecommendation")
    public ResponseEntity<RecommendationEntity> getRecommendationByMail() {
        RecommendationEntity recommendation = chatGPTService.getRecommendationByMail();
        return ResponseEntity.ok(recommendation) ;
    }
}

package com.khalid.hms.CDSS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommendation")
public class RecommendationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID recommendationId;
    private String mail;
    private String diagnosis;
    private String treatmentPlan;
    private String medicationRecommendation;
}


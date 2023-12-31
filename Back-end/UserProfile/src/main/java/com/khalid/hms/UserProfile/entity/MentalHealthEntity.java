package com.khalid.hms.UserProfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mental-health")
public class MentalHealthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mood;
    private LocalDate presentDate;

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private UserProfileEntity userProfileEntity;
}

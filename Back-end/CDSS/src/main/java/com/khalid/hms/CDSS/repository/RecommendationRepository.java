package com.khalid.hms.CDSS.repository;


import com.khalid.hms.CDSS.entity.RecommendationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long> {
    Optional<RecommendationEntity> findByMail(String mail);
}

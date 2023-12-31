package com.khalid.hms.UserProfile.repository;

import com.khalid.hms.UserProfile.entity.HealthDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthDataRepository extends JpaRepository<HealthDataEntity,String> {
    Optional<HealthDataEntity> findById(String id);
}

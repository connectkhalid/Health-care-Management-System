package com.khalid.hms.UserProfile.repository;

import com.khalid.hms.UserProfile.entity.MentalHealthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentalHealthRepository extends JpaRepository<MentalHealthEntity,String> {
}

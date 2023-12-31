package com.khalid.hms.DoctorProfile.repository;

import com.khalid.hms.DoctorProfile.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findAllByDoctorId(String doctorId);
    ReviewEntity findByDoctorIdAndPatientId(String doctorId, String patientId);
}

package com.khalid.hms.DoctorProfile.repository;


import com.khalid.hms.DoctorProfile.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, String> {
    Optional<DoctorEntity> findByEmail(String email);
    List<DoctorEntity> findAllByIsAvailableIsTrue();
    List<DoctorEntity> findAllByDepartment(String department);
}

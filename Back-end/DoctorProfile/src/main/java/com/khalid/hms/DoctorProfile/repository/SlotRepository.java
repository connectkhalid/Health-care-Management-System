package com.khalid.hms.DoctorProfile.repository;

import com.khalid.hms.DoctorProfile.entity.SlotEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<SlotEntity, String> {
    List<SlotEntity> findAllByDoctorIdAndAndSlotDateIsGreaterThanEqualOrderByStartTime(String doctorId, LocalDate slotDate);
    @Query("SELECT s FROM SlotEntity s " + "WHERE s.doctorId = :doctorId " +
            "AND s.slotDate = :slotDate " + "AND s.isAvailable = true")
    List<SlotEntity> findAvailableSlotsByDoctorIdAndDate(@Param("doctorId") String doctorId, @Param("slotDate") LocalDate slotDate);

    // This query method retrieves slots of the current day for a specific doctor
    List<SlotEntity> findSlotEntitiesByDoctorIdAndSlotDate(String doctorId, LocalDate startDate);
    List<SlotEntity> findAllByDoctorIdAndSlotDate(String doctorId, LocalDate slotDate);
}
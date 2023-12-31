package com.khalid.hms.PharmacyInventory.repository;

import com.khalid.hms.PharmacyInventory.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<MedicineEntity, Integer> {
    Optional<MedicineEntity> findByMedicineName(String medicineName);
    Optional<List<MedicineEntity>> findAllByIsAvailableIsTrue();
    Optional<List<MedicineEntity>> findAllByMedicineTypeOrderByMedicineName(String medicineType);
}
package com.khalid.hms.PharmacyInventory.service;

import com.khalid.hms.PharmacyInventory.dto.MedicineDto;
import com.khalid.hms.PharmacyInventory.dto.MedicineResponseDto;

import java.util.List;

public interface MedicineService {
    MedicineResponseDto addMedicine(MedicineDto medicineDto);
    MedicineDto updateMedicine( MedicineDto medicineDto);
    MedicineResponseDto deleteMedicineById(String medicineName);
    List<MedicineDto> getAllMedicines();
    MedicineDto getMedicineById(int id);
    List<MedicineDto> getAllAvailableMedicine();
    List<MedicineDto> sortByMedicineType(String medicineType);
}
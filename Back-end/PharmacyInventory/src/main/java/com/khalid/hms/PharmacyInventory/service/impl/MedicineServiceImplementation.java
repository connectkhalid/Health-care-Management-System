package com.khalid.hms.PharmacyInventory.service.impl;

import com.khalid.hms.PharmacyInventory.dto.MedicineDto;
import com.khalid.hms.PharmacyInventory.dto.MedicineResponseDto;
import com.khalid.hms.PharmacyInventory.entity.MedicineEntity;
import com.khalid.hms.PharmacyInventory.exceptions.AlreadyExistsException;
import com.khalid.hms.PharmacyInventory.exceptions.ResourceNotFoundException;
import com.khalid.hms.PharmacyInventory.repository.MedicineRepository;
import com.khalid.hms.PharmacyInventory.service.MedicineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MedicineServiceImplementation implements MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MedicineServiceImplementation(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MedicineResponseDto addMedicine(MedicineDto medicineDto) {
        Optional<MedicineEntity> medicine = medicineRepository.findByMedicineName(medicineDto.getMedicineName());
        if (medicine.isPresent())
            throw new AlreadyExistsException("Medicine Exists with same name");
        MedicineEntity medicineEntity = modelMapper.map(medicineDto, MedicineEntity.class);
        medicineRepository.save(medicineEntity);
        return new MedicineResponseDto("Medicine is Added!", true);
    }

    @Override
    public MedicineDto updateMedicine(MedicineDto medicineDto) {
        Optional<MedicineEntity> medicine = medicineRepository
                .findByMedicineName(medicineDto.getMedicineName());
        if (medicine.isPresent()) {
            MedicineEntity existingMedicine = medicine.get();
            modelMapper.map(medicineDto, existingMedicine);
            MedicineEntity updatedMedicine = medicineRepository.save(existingMedicine);
            return modelMapper.map(updatedMedicine, MedicineDto.class);
        } else {
            throw new ResourceNotFoundException("Medicine is  not found for medicine Name: "
                    + medicineDto.getMedicineName());
        }
    }

    @Override
    public MedicineResponseDto deleteMedicineById(String medicineName) {
        Optional<MedicineEntity> optionalMedicine = medicineRepository.findByMedicineName(medicineName);

        if (optionalMedicine.isPresent()) {
            medicineRepository.delete(optionalMedicine.get());
            return new MedicineResponseDto("The Record has been Deleted!", true);
        } else {
            throw new ResourceNotFoundException("Medicine is  not found for medicine Name: " + medicineName);
        }
    }

    public List<MedicineDto> getAllMedicines() {
        List<MedicineEntity> medicineEntities = medicineRepository.findAll();
        if (medicineEntities.isEmpty())
            throw new ResourceNotFoundException("No Medicine Found");

        return ConvertListOfEntityToDto(Optional.of(medicineEntities));
    }

    public MedicineDto getMedicineById(int id) {
        Optional<MedicineEntity> optionalMedicine = medicineRepository.findById(id);

        if (optionalMedicine.isPresent()) {
            MedicineEntity medicineEntity = optionalMedicine.get();
            return convertToDto(medicineEntity);
        } else {
            throw new ResourceNotFoundException("Medicine is  not found for medicine ID: " + id);
        }
    }

    @Override
    public List<MedicineDto> getAllAvailableMedicine() {
        Optional<List<MedicineEntity>> availableMedicines = medicineRepository.findAllByIsAvailableIsTrue();
        return ConvertListOfEntityToDto(availableMedicines);
    }

    @Override
    public List<MedicineDto> sortByMedicineType(String medicineType) {
        Optional<List<MedicineEntity>> sortedMedicines = medicineRepository.findAllByMedicineTypeOrderByMedicineName(medicineType);
        return ConvertListOfEntityToDto(sortedMedicines);
    }

    private List<MedicineDto> ConvertListOfEntityToDto(Optional<List<MedicineEntity>> sortedMedicines) {
        if (sortedMedicines.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, No available medicine is found!");
        }
        List<MedicineDto> sortedMedicineDtoList = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (MedicineEntity medicineEntity : sortedMedicines.get()) {
            sortedMedicineDtoList.add(mapper.map(medicineEntity, MedicineDto.class));
        }
        return sortedMedicineDtoList;
    }

    private MedicineDto convertToDto(MedicineEntity medicineEntity) {
        // Use a model mapper or manually map fields from the entity to the DTO
        MedicineDto medicineDto = new MedicineDto();
        medicineDto.setMedicineName(medicineEntity.getMedicineName());
        medicineDto.setGeneric(medicineEntity.getGeneric());
        medicineDto.setManufacturer(medicineEntity.getManufacturer());
        medicineDto.setManufacturingDate(medicineEntity.getManufacturingDate());
        medicineDto.setExpirationDate(medicineEntity.getExpirationDate());
        medicineDto.setMedicineType(medicineEntity.getMedicineType());
        medicineDto.setIsAvailable(medicineEntity.getIsAvailable());
        return medicineDto;
    }
}


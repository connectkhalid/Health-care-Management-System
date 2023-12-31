package com.khalid.hms.PharmacyInventory.controller;

import com.khalid.hms.PharmacyInventory.dto.MedicineResponseDto;
import com.khalid.hms.PharmacyInventory.service.MedicineService;
import com.khalid.hms.PharmacyInventory.dto.MedicineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    /**
     * Add a new medicine.
     *
     * @param medicineDto The data of the medicine to be added.
     * @return ResponseEntity with the response for the added medicine.
     */
    @PostMapping("/add")
    public ResponseEntity<MedicineResponseDto> addMedicine(@RequestBody MedicineDto medicineDto) {
        try {
            // Attempt to add the medicine
            MedicineResponseDto responseDto = medicineService.addMedicine(medicineDto);
            log.info("Medicine created");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            // Handle exceptions and return a failure response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MedicineResponseDto("Failed to add medicine! Reason: " + ex.getMessage()));
        }
    }

    /**
     * Update an existing medicine.
     *
     * @param medicineDto The updated data for the medicine.
     * @return ResponseEntity with the response for the updated medicine.
     */
    @PutMapping("/update")
    public ResponseEntity<Object> updateMedicine(@RequestBody MedicineDto medicineDto) {
        try {
            // Attempt to update the medicine
            MedicineDto updatedMedicine = medicineService.updateMedicine(medicineDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedMedicine);
        } catch (Exception ex) {
            // Handle exceptions and return a failure response
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new MedicineResponseDto("Failed to update medicine! Reason: " + ex.getMessage()));
        }
    }

    /**
     * Delete a medicine by its name.
     *
     * @param medicineName The name of the medicine to be deleted.
     * @return ResponseEntity with the response for the deleted medicine.
     */
    @DeleteMapping("/delete/{medicineName}")
    public ResponseEntity<MedicineResponseDto> deleteMedicine(@PathVariable String medicineName) {
        try {
            // Attempt to delete the medicine
            MedicineResponseDto responseDto = medicineService.deleteMedicineById(medicineName);
            return ResponseEntity.ok(responseDto);
        } catch (Exception ex) {
            // Handle exceptions and return a failure response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MedicineResponseDto("Failed to delete medicine! Reason: " + ex.getMessage()));
        }
    }

    /**
     * Get a list of all medicines.
     *
     * @return ResponseEntity with the list of all medicines.
     */
    @GetMapping("/all-medicine")
    public ResponseEntity<Object> getAllMedicines() {
        try {
            // Retrieve the list of all medicines
            List<MedicineDto> medicines = medicineService.getAllMedicines();
            return ResponseEntity.status(HttpStatus.OK).body(medicines);
        } catch (Exception ex) {
            // Handle exceptions and return a failure response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MedicineResponseDto("Failed to retrieve all medicines! Reason: " + ex.getMessage()));
        }
    }

    /**
     * Get a medicine by its ID.
     *
     * @param id The ID of the medicine to be retrieved.
     * @return ResponseEntity with the response for the retrieved medicine.
     */
    @GetMapping("/medicine-by-id/{id}")
    public ResponseEntity<Object> getMedicineById(@PathVariable int id) {
        try {
            // Retrieve the medicine by ID
            MedicineDto medicineDto = medicineService.getMedicineById(id);
            return ResponseEntity.ok(medicineDto);
        } catch (Exception ex) {
            // Handle exceptions and return a failure response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MedicineResponseDto("Failed to retrieve medicine by id! Reason: " + ex.getMessage()));
        }
    }

    /**
     * Get a list of all available medicines.
     *
     * @return ResponseEntity with the list of all available medicines.
     */
    @GetMapping("/all-available-medicine")
    public ResponseEntity<Object> getAllAvailableMedicines() {
        try {
            // Retrieve the list of all available medicines
            List<MedicineDto> medicines = medicineService.getAllAvailableMedicine();
            return ResponseEntity.status(HttpStatus.OK).body(medicines);
        } catch (Exception ex) {
            // Handle exceptions and return a failure response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MedicineResponseDto("Failed to retrieve medicine by id! Reason: " + ex.getMessage()));
        }
    }
}

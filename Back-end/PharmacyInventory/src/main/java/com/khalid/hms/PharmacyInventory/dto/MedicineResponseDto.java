package com.khalid.hms.PharmacyInventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponseDto {
    private String message;
    private Boolean response;

    public MedicineResponseDto(String s) {
    }
}

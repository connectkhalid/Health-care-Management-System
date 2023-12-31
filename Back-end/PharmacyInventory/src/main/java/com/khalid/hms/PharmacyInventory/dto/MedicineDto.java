package com.khalid.hms.PharmacyInventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {
        @NotBlank(message = "Medicine name cannot be blank")
        private String medicineName;

        @NotBlank(message = "Generic cannot be blank")
        private String generic;

        @NotBlank(message = "Manufacturer cannot be blank")
        private String manufacturer;

        @NotNull(message = "Manufacturing date cannot be null")
        private Date manufacturingDate;

        @NotNull(message = "Expiration date cannot be null")
        private Date expirationDate;

        @NotBlank(message = "Medicine type cannot be blank")
        private String medicineType;

        @NotNull(message = "Availability status cannot be null")
        private Boolean isAvailable;
}

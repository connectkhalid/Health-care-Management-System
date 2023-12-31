package com.khalid.hms.PharmacyInventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Medicine")
public class MedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String medicineName;

    @Column(nullable = false)
    private String generic;

    @Column(nullable = false)
    private String manufacturer;

    @Column(name = "manufacturingDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date manufacturingDate;

    @Column(name = "expirationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @Column(nullable = false)
    private String medicineType;

    @Column(nullable = false)
    private Boolean isAvailable;

}
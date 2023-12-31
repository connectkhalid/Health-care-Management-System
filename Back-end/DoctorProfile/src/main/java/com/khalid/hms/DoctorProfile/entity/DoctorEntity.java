package com.khalid.hms.DoctorProfile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor_table")
public class DoctorEntity {
        @Id
        private String doctor_id;
        private String name;
        private String email;
        private String password;
        private String image;
        private String role;
        private String gender;
        private String registration_number_BDMC;
        private String department;
        private String allocated_room;
        private String qualifications;
        private Boolean isValid;
        private Boolean isAvailable;
        private LocalDate created_at;
}
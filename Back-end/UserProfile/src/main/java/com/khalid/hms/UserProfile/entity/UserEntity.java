package com.khalid.hms.UserProfile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient_table")
public class UserEntity{
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String gender;
    private Integer age;
    private String address;
    private String image;
    private String contactInfo;
    private Boolean isVerified;
    private LocalDate createdAt;
}
package com.research.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearcherDto {
    private Long id;
    private String name;
    private String email;
    private String designation;
    private String institute;
    private String purpose;
    private Boolean isvalid;
    private Boolean isTaken;
}

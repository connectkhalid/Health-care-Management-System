package com.khalid.hms.CommunityPortal.dto;

import com.khalid.hms.CommunityPortal.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private Long voteId;
    private String patientId;
    private Long postId;  // Assuming you want to include postId in the DTO
    private VoteType voteType;
}

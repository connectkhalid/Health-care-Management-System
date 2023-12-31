package com.khalid.hms.CommunityPortal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteCountDto {
    private Long postId;
    private Long upVote;
    private Long downVote;
}

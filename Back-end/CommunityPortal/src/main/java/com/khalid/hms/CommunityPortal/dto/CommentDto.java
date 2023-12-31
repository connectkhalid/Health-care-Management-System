package com.khalid.hms.CommunityPortal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long commentId;
    private String patientId;
    private String Name;
    private Long postId;
    private String commentContent;
    private LocalDateTime commentTime;
    private Boolean isAvailable;
}

package com.khalid.hms.CommunityPortal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long postId;
    @NotNull(message = "patientID is empty")
    private String patientId;
    @NotNull(message = "postTitle is empty")
    private String postTitle;
    @NotNull(message = "postContent is empty")
    private String postContent;
    @NotNull(message = "catagory is empty")
    private String category;
    private LocalDateTime postTime;
}

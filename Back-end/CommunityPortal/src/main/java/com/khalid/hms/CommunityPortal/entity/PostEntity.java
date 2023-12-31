package com.khalid.hms.CommunityPortal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String patientId;

    private String postTitle;

    @Lob
    private String postContent;

    private String category;

    private LocalDateTime postTime;
}

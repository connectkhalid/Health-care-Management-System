package com.khalid.hms.CommunityPortal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;
    @Column(name = "name", nullable = false)
    private String Name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(name = "comment_content", nullable = false)
    @Lob
    private String commentContent;

    @Column(name = "comment_time", nullable = false)
    private LocalDateTime commentTime;
    private Boolean isAvailable;

}

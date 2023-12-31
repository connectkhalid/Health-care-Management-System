package com.khalid.hms.CommunityPortal.repository;

import com.khalid.hms.CommunityPortal.entity.CommentEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.post.id = :postId")
    List<CommentEntity> findByPostId(@Param("postId") Long postId);
    List<CommentEntity> findAllByPost_PostIdOrderByCommentTime(Long postId);
}

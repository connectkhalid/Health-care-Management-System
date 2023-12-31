package com.khalid.hms.CommunityPortal.repository;

import com.khalid.hms.CommunityPortal.entity.VoteEntity;
import com.khalid.hms.CommunityPortal.enums.VoteType;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteEntity,Long> {
    @Query("SELECT COUNT(v) FROM VoteEntity v WHERE v.post.postId = :postId AND v.voteType = :voteType")
    Long countByPostIdAndVoteType(@Param("postId") Long postId, @Param("voteType") VoteType voteType);
    @Query("SELECT v FROM VoteEntity v WHERE v.patientId = :patientId AND v.post.postId = :postId")
    Optional<VoteEntity> findByPatientIdAndPostId(@Param("patientId") String patientId, @Param("postId") Long postId);


}

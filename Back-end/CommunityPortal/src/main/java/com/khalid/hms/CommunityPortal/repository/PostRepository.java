package com.khalid.hms.CommunityPortal.repository;

import com.khalid.hms.CommunityPortal.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
}

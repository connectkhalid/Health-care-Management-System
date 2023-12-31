package com.research.repo;

import com.research.dto.ResearcherDto;
import com.research.entity.ResearcherEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResearcherRepository extends JpaRepository<ResearcherEntity, Long> {
    Optional<ResearcherDto> findByEmail(String email);

    List<ResearcherEntity> findByIsTaken(boolean isTaken);
//    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.id = :id AND u.isValid = true")
//    boolean findByIdAndIsValid(@Param("id") Long id);

    ResearcherEntity findByIdAndIsvalidIsFalse(Long id);
}

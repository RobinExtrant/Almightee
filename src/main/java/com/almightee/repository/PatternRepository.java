package com.almightee.repository;

import com.almightee.domain.Pattern;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pattern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {
    @Modifying
    @Query("UPDATE Pattern p SET p.url = :url WHERE c.id = :id")
    int updatePatternUrl(@Param("id") Long id, @Param("url") String url);
}

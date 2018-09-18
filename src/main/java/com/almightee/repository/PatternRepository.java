package com.almightee.repository;

import com.almightee.domain.Pattern;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pattern entity.
 */
@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {

}

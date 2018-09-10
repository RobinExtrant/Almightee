package com.almightee.repository;

import com.almightee.domain.Pattern;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pattern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {

}

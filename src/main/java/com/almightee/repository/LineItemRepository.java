package com.almightee.repository;

import com.almightee.domain.LineItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LineItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}

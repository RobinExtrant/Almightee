package com.almightee.repository;

import com.almightee.domain.CommandItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommandItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandItemRepository extends JpaRepository<CommandItem, Long> {

}

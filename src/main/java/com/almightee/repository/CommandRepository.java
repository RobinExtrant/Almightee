package com.almightee.repository;

import com.almightee.domain.Command;
import com.almightee.domain.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Command entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
    List<Command> findAllByCustomer(Customer customer);
}

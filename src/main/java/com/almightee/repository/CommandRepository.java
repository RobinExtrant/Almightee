package com.almightee.repository;

import com.almightee.domain.Command;
/*
import com.almightee.domain.Customer;
*/
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Command entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    //public List<Command> findByCustomer(Long customer_id);
    public List<Command> findByUser(Long user_id);

    //public Optional<Command> findByCustomerIdAndId(Long customer_id, Long id);
    public Optional<Command> findByUserIdAndId(Long user_id, Long id);
}

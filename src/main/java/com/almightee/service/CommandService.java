package com.almightee.service;

import com.almightee.domain.Command;
import com.almightee.domain.Customer;
import com.almightee.domain.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommandService {
    public List<Command> retrieveAllCommandsByUser(Customer customer);
    public Optional<Command> getCommand(Long id);
    public Command saveCommand(Command command);
    public void deleteCommand(Long id);

    public List<Command> retrieveAllCommands();
}

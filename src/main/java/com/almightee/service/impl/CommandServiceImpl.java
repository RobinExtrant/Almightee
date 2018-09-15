package com.almightee.service.impl;

import com.almightee.domain.Command;
import com.almightee.domain.Customer;
import com.almightee.domain.Pattern;
import com.almightee.repository.CommandRepository;
import com.almightee.repository.search.CommandSearchRepository;
import com.almightee.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommandServiceImpl implements CommandService {

    private final Logger log = LoggerFactory.getLogger(CommandServiceImpl.class);

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private CommandSearchRepository commandSearchRepository;

    @Override
    public List<Command> retrieveAllCommandsByUser(Customer customer) {
        return commandRepository.findAllByCustomer(customer);
    }

    @Override
    public Optional<Command> getCommand(Long id) {
        return commandRepository.findById(id);
    }

    @Override
    public Command saveCommand(Command command) {
        Command result = commandRepository.save(command);
        commandSearchRepository.save(result);
        return result;
    }

    @Override
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
        commandSearchRepository.deleteById(id);
    }

    @Override
    public List<Command> retrieveAllCommands() {
        return commandRepository.findAll();
    }
}

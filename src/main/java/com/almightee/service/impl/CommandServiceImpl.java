package com.almightee.service.impl;

import com.almightee.domain.Command;
//import com.almightee.domain.Customer;
import com.almightee.domain.Pattern;
import com.almightee.domain.enumeration.CommandStatus;
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
    public Optional<Command> getCommand(Long idUser, Long idCommand) {
        return commandRepository.findByUserIdAndId(idUser, idCommand);
    }

    @Override
    public Command saveCommand(Command command) {
        command.setStatus(CommandStatus.IN_PREPARATION);
        Command result = commandRepository.save(command);
        commandSearchRepository.save(result);
        return result;
    }

    @Override
    public void deleteCommand(Long idUser, Long idCommand) {
        Optional<Command> command = commandRepository.findByUserIdAndId(idUser, idCommand);
        if(command.isPresent()) {
            commandRepository.delete(command.get());
            commandSearchRepository.deleteById(idCommand);
        }
    }

    @Override
    public List<Command> retrieveAllCommands(Long idUser) {
        return commandRepository.findByUser(idUser);
    }
}

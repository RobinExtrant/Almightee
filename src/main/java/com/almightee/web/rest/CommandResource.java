package com.almightee.web.rest;

import com.almightee.domain.Customer;
import com.almightee.service.CommandService;
import com.almightee.service.dto.UserDTO;
import com.codahale.metrics.annotation.Timed;
import com.almightee.domain.Command;
import com.almightee.repository.CommandRepository;
import com.almightee.repository.search.CommandSearchRepository;
import com.almightee.web.rest.errors.BadRequestAlertException;
import com.almightee.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Command.
 */
@RestController
@RequestMapping("/api")
public class CommandResource {

    private final Logger log = LoggerFactory.getLogger(CommandResource.class);

    private static final String ENTITY_NAME = "command";

    @Autowired
    private CommandService commandService;

    public CommandResource() {
    }

    /**
     * POST  /commands : Create a new command.
     *
     * @param command the command to create
     * @return the ResponseEntity with status 201 (Created) and with body the new command, or with status 400 (Bad Request) if the command has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commands")
    @Timed
    public ResponseEntity<Command> createCommand(@RequestBody Command command) throws URISyntaxException {
        log.debug("REST request to save Command : {}", command);
        if (command.getId() != null) {
            throw new BadRequestAlertException("A new command cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Command result = commandService.saveCommand(command);
        return ResponseEntity.created(new URI("/api/commands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commands : Updates an existing command.
     *
     * @param command the command to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated command,
     * or with status 400 (Bad Request) if the command is not valid,
     * or with status 500 (Internal Server Error) if the command couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commands")
    @Timed
    public ResponseEntity<Command> updateCommand(@RequestBody Command command) throws URISyntaxException {
        log.debug("REST request to update Command : {}", command);
        if (command.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Command result = commandService.saveCommand(command);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, command.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commands : get all the commands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commands in body
     */
    @GetMapping("/commands")
    @Timed
    public List<Command> getAllCommands() throws URISyntaxException {
        log.debug("REST request to get all Commands of the connected customer");
        return commandService.retrieveAllCommands();
    }

    /**
     * GET  /commands/:id : get the "id" command.
     *
     * @param id the id of the command to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the command, or with status 404 (Not Found)
     */
    @GetMapping("/commands/{id}")
    @Timed
    public ResponseEntity<Command> getCommand(@PathVariable Long id) {
        log.debug("REST request to get Command : {}", id);
        Optional<Command> command = commandService.getCommand(id);
        return ResponseUtil.wrapOrNotFound(command);
    }

    /**
     * DELETE  /commands/:id : delete the "id" command.
     *
     * @param id the id of the command to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commands/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommand(@PathVariable Long id) {
        log.debug("REST request to delete Command : {}", id);
        commandService.deleteCommand(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commands?query=:query : search for the command corresponding
     * to the query.
     *
     * @param query the query of the command search
     * @return the result of the search
     */
//    @GetMapping("/_search/commands")
//    @Timed
//    public List<Command> searchCommands(@RequestParam String query) {
//        log.debug("REST request to search Commands for query {}", query);
//        return StreamSupport
//            .stream(commandSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .collect(Collectors.toList());
//    }

}

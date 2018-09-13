package com.almightee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.almightee.domain.CommandItem;
import com.almightee.repository.CommandItemRepository;
import com.almightee.repository.search.CommandItemSearchRepository;
import com.almightee.web.rest.errors.BadRequestAlertException;
import com.almightee.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing CommandItem.
 */
@RestController
@RequestMapping("/api")
public class CommandItemResource {

    private final Logger log = LoggerFactory.getLogger(CommandItemResource.class);

    private static final String ENTITY_NAME = "commandItem";

    private final CommandItemRepository commandItemRepository;

    private final CommandItemSearchRepository commandItemSearchRepository;

    public CommandItemResource(CommandItemRepository commandItemRepository, CommandItemSearchRepository commandItemSearchRepository) {
        this.commandItemRepository = commandItemRepository;
        this.commandItemSearchRepository = commandItemSearchRepository;
    }

    /**
     * POST  /command-items : Create a new commandItem.
     *
     * @param commandItem the commandItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commandItem, or with status 400 (Bad Request) if the commandItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/command-items")
    @Timed
    public ResponseEntity<CommandItem> createCommandItem(@RequestBody CommandItem commandItem) throws URISyntaxException {
        log.debug("REST request to save CommandItem : {}", commandItem);
        if (commandItem.getId() != null) {
            throw new BadRequestAlertException("A new commandItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandItem result = commandItemRepository.save(commandItem);
        commandItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/command-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /command-items : Updates an existing commandItem.
     *
     * @param commandItem the commandItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandItem,
     * or with status 400 (Bad Request) if the commandItem is not valid,
     * or with status 500 (Internal Server Error) if the commandItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/command-items")
    @Timed
    public ResponseEntity<CommandItem> updateCommandItem(@RequestBody CommandItem commandItem) throws URISyntaxException {
        log.debug("REST request to update CommandItem : {}", commandItem);
        if (commandItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommandItem result = commandItemRepository.save(commandItem);
        commandItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commandItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /command-items : get all the commandItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commandItems in body
     */
    @GetMapping("/command-items")
    @Timed
    public List<CommandItem> getAllCommandItems() {
        log.debug("REST request to get all CommandItems");
        return commandItemRepository.findAll();
    }

    /**
     * GET  /command-items/:id : get the "id" commandItem.
     *
     * @param id the id of the commandItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandItem, or with status 404 (Not Found)
     */
    @GetMapping("/command-items/{id}")
    @Timed
    public ResponseEntity<CommandItem> getCommandItem(@PathVariable Long id) {
        log.debug("REST request to get CommandItem : {}", id);
        Optional<CommandItem> commandItem = commandItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commandItem);
    }

    /**
     * DELETE  /command-items/:id : delete the "id" commandItem.
     *
     * @param id the id of the commandItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/command-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommandItem(@PathVariable Long id) {
        log.debug("REST request to delete CommandItem : {}", id);

        commandItemRepository.deleteById(id);
        commandItemSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/command-items?query=:query : search for the commandItem corresponding
     * to the query.
     *
     * @param query the query of the commandItem search
     * @return the result of the search
     */
    @GetMapping("/_search/command-items")
    @Timed
    public List<CommandItem> searchCommandItems(@RequestParam String query) {
        log.debug("REST request to search CommandItems for query {}", query);
        return StreamSupport
            .stream(commandItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}

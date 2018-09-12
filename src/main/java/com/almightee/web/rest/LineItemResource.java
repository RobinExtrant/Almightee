package com.almightee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.almightee.domain.LineItem;
import com.almightee.repository.LineItemRepository;
import com.almightee.repository.search.LineItemSearchRepository;
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
 * REST controller for managing LineItem.
 */
@RestController
@RequestMapping("/api")
public class LineItemResource {

    private final Logger log = LoggerFactory.getLogger(LineItemResource.class);

    private static final String ENTITY_NAME = "lineItem";

    private final LineItemRepository lineItemRepository;

    private final LineItemSearchRepository lineItemSearchRepository;

    public LineItemResource(LineItemRepository lineItemRepository, LineItemSearchRepository lineItemSearchRepository) {
        this.lineItemRepository = lineItemRepository;
        this.lineItemSearchRepository = lineItemSearchRepository;
    }

    /**
     * POST  /line-items : Create a new lineItem.
     *
     * @param lineItem the lineItem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lineItem, or with status 400 (Bad Request) if the lineItem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/line-items")
    @Timed
    public ResponseEntity<LineItem> createLineItem(@RequestBody LineItem lineItem) throws URISyntaxException {
        log.debug("REST request to save LineItem : {}", lineItem);
        if (lineItem.getId() != null) {
            throw new BadRequestAlertException("A new lineItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineItem result = lineItemRepository.save(lineItem);
        lineItemSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/line-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /line-items : Updates an existing lineItem.
     *
     * @param lineItem the lineItem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lineItem,
     * or with status 400 (Bad Request) if the lineItem is not valid,
     * or with status 500 (Internal Server Error) if the lineItem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/line-items")
    @Timed
    public ResponseEntity<LineItem> updateLineItem(@RequestBody LineItem lineItem) throws URISyntaxException {
        log.debug("REST request to update LineItem : {}", lineItem);
        if (lineItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LineItem result = lineItemRepository.save(lineItem);
        lineItemSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lineItem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /line-items : get all the lineItems.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lineItems in body
     */
    @GetMapping("/line-items")
    @Timed
    public List<LineItem> getAllLineItems() {
        log.debug("REST request to get all LineItems");
        return lineItemRepository.findAll();
    }

    /**
     * GET  /line-items/:id : get the "id" lineItem.
     *
     * @param id the id of the lineItem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lineItem, or with status 404 (Not Found)
     */
    @GetMapping("/line-items/{id}")
    @Timed
    public ResponseEntity<LineItem> getLineItem(@PathVariable Long id) {
        log.debug("REST request to get LineItem : {}", id);
        Optional<LineItem> lineItem = lineItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lineItem);
    }

    /**
     * DELETE  /line-items/:id : delete the "id" lineItem.
     *
     * @param id the id of the lineItem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/line-items/{id}")
    @Timed
    public ResponseEntity<Void> deleteLineItem(@PathVariable Long id) {
        log.debug("REST request to delete LineItem : {}", id);

        lineItemRepository.deleteById(id);
        lineItemSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/line-items?query=:query : search for the lineItem corresponding
     * to the query.
     *
     * @param query the query of the lineItem search
     * @return the result of the search
     */
    @GetMapping("/_search/line-items")
    @Timed
    public List<LineItem> searchLineItems(@RequestParam String query) {
        log.debug("REST request to search LineItems for query {}", query);
        return StreamSupport
            .stream(lineItemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}

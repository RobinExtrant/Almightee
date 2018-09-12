package com.almightee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.almightee.domain.Pattern;
import com.almightee.repository.PatternRepository;
import com.almightee.repository.search.PatternSearchRepository;
import com.almightee.web.rest.errors.BadRequestAlertException;
import com.almightee.web.rest.util.HeaderUtil;
import com.almightee.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing Pattern.
 */
@RestController
@RequestMapping("/api")
public class PatternResource {

    private final Logger log = LoggerFactory.getLogger(PatternResource.class);

    private static final String ENTITY_NAME = "pattern";

    private final PatternRepository patternRepository;

    private final PatternSearchRepository patternSearchRepository;

    public PatternResource(PatternRepository patternRepository, PatternSearchRepository patternSearchRepository) {
        this.patternRepository = patternRepository;
        this.patternSearchRepository = patternSearchRepository;
    }

    /**
     * POST  /patterns : Create a new pattern.
     *
     * @param pattern the pattern to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pattern, or with status 400 (Bad Request) if the pattern has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patterns")
    @Timed
    public ResponseEntity<Pattern> createPattern(@RequestBody Pattern pattern) throws URISyntaxException {
        log.debug("REST request to save Pattern : {}", pattern);
        if (pattern.getId() != null) {
            throw new BadRequestAlertException("A new pattern cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pattern result = patternRepository.save(pattern);
        patternSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/patterns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patterns : Updates an existing pattern.
     *
     * @param pattern the pattern to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pattern,
     * or with status 400 (Bad Request) if the pattern is not valid,
     * or with status 500 (Internal Server Error) if the pattern couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patterns")
    @Timed
    public ResponseEntity<Pattern> updatePattern(@RequestBody Pattern pattern) throws URISyntaxException {
        log.debug("REST request to update Pattern : {}", pattern);
        if (pattern.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pattern result = patternRepository.save(pattern);
        patternSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pattern.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patterns : get all the patterns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of patterns in body
     */
    @GetMapping("/patterns")
    @Timed
    public ResponseEntity<List<Pattern>> getAllPatterns(Pageable pageable) {
        log.debug("REST request to get a page of Patterns");
        Page<Pattern> page = patternRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patterns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /patterns/:id : get the "id" pattern.
     *
     * @param id the id of the pattern to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pattern, or with status 404 (Not Found)
     */
    @GetMapping("/patterns/{id}")
    @Timed
    public ResponseEntity<Pattern> getPattern(@PathVariable Long id) {
        log.debug("REST request to get Pattern : {}", id);
        Optional<Pattern> pattern = patternRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pattern);
    }

    /**
     * DELETE  /patterns/:id : delete the "id" pattern.
     *
     * @param id the id of the pattern to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patterns/{id}")
    @Timed
    public ResponseEntity<Void> deletePattern(@PathVariable Long id) {
        log.debug("REST request to delete Pattern : {}", id);

        patternRepository.deleteById(id);
        patternSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/patterns?query=:query : search for the pattern corresponding
     * to the query.
     *
     * @param query the query of the pattern search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/patterns")
    @Timed
    public ResponseEntity<List<Pattern>> searchPatterns(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Patterns for query {}", query);
        Page<Pattern> page = patternSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/patterns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}

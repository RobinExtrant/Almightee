package com.almightee.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.almightee.domain.Cart;
import com.almightee.repository.CartRepository;
import com.almightee.repository.search.CartSearchRepository;
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
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "cart";

    private final CartRepository cartRepository;

    private final CartSearchRepository cartSearchRepository;

    public CartResource(CartRepository cartRepository, CartSearchRepository cartSearchRepository) {
        this.cartRepository = cartRepository;
        this.cartSearchRepository = cartSearchRepository;
    }

    /**
     * POST  /carts : Create a new cart.
     *
     * @param cart the cart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cart, or with status 400 (Bad Request) if the cart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carts")
    @Timed
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cart);
        if (cart.getId() != null) {
            throw new BadRequestAlertException("A new cart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cart result = cartRepository.save(cart);
        cartSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carts : Updates an existing cart.
     *
     * @param cart the cart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cart,
     * or with status 400 (Bad Request) if the cart is not valid,
     * or with status 500 (Internal Server Error) if the cart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carts")
    @Timed
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cart);
        if (cart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cart result = cartRepository.save(cart);
        cartSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carts : get all the carts.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of carts in body
     */
    @GetMapping("/carts")
    @Timed
    public List<Cart> getAllCarts(@RequestParam(required = false) String filter) {
        if ("customer-is-null".equals(filter)) {
            log.debug("REST request to get all Carts where customer is null");
            return StreamSupport
                .stream(cartRepository.findAll().spliterator(), false)
                .filter(cart -> cart.getCustomer() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Carts");
        return cartRepository.findAll();
    }

    /**
     * GET  /carts/:id : get the "id" cart.
     *
     * @param id the id of the cart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cart, or with status 404 (Not Found)
     */
    @GetMapping("/carts/{id}")
    @Timed
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        log.debug("REST request to get Cart : {}", id);
        Optional<Cart> cart = cartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cart);
    }

    /**
     * DELETE  /carts/:id : delete the "id" cart.
     *
     * @param id the id of the cart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);

        cartRepository.deleteById(id);
        cartSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/carts?query=:query : search for the cart corresponding
     * to the query.
     *
     * @param query the query of the cart search
     * @return the result of the search
     */
    @GetMapping("/_search/carts")
    @Timed
    public List<Cart> searchCarts(@RequestParam String query) {
        log.debug("REST request to search Carts for query {}", query);
        return StreamSupport
            .stream(cartSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}

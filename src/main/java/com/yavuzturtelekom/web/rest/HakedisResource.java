package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.service.HakedisService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.HakedisCriteria;
import com.yavuzturtelekom.service.HakedisQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Hakedis.
 */
@RestController
@RequestMapping("/api")
public class HakedisResource {

    private final Logger log = LoggerFactory.getLogger(HakedisResource.class);

    private static final String ENTITY_NAME = "hakedis";

    private final HakedisService hakedisService;

    private final HakedisQueryService hakedisQueryService;

    public HakedisResource(HakedisService hakedisService, HakedisQueryService hakedisQueryService) {
        this.hakedisService = hakedisService;
        this.hakedisQueryService = hakedisQueryService;
    }

    /**
     * POST  /hakedis : Create a new hakedis.
     *
     * @param hakedis the hakedis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hakedis, or with status 400 (Bad Request) if the hakedis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hakedis")
    public ResponseEntity<Hakedis> createHakedis(@Valid @RequestBody Hakedis hakedis) throws URISyntaxException {
        log.debug("REST request to save Hakedis : {}", hakedis);
        if (hakedis.getId() != null) {
            throw new BadRequestAlertException("A new hakedis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hakedis result = hakedisService.save(hakedis);
        return ResponseEntity.created(new URI("/api/hakedis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hakedis : Updates an existing hakedis.
     *
     * @param hakedis the hakedis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hakedis,
     * or with status 400 (Bad Request) if the hakedis is not valid,
     * or with status 500 (Internal Server Error) if the hakedis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hakedis")
    public ResponseEntity<Hakedis> updateHakedis(@Valid @RequestBody Hakedis hakedis) throws URISyntaxException {
        log.debug("REST request to update Hakedis : {}", hakedis);
        if (hakedis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hakedis result = hakedisService.save(hakedis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hakedis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hakedis : get all the hakedis.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of hakedis in body
     */
    @GetMapping("/hakedis")
    public ResponseEntity<List<Hakedis>> getAllHakedis(HakedisCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Hakedis by criteria: {}", criteria);
        Page<Hakedis> page = hakedisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hakedis");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /hakedis/count : count all the hakedis.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/hakedis/count")
    public ResponseEntity<Long> countHakedis(HakedisCriteria criteria) {
        log.debug("REST request to count Hakedis by criteria: {}", criteria);
        return ResponseEntity.ok().body(hakedisQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /hakedis/:id : get the "id" hakedis.
     *
     * @param id the id of the hakedis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hakedis, or with status 404 (Not Found)
     */
    @GetMapping("/hakedis/{id}")
    public ResponseEntity<Hakedis> getHakedis(@PathVariable Long id) {
        log.debug("REST request to get Hakedis : {}", id);
        Optional<Hakedis> hakedis = hakedisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hakedis);
    }

    /**
     * DELETE  /hakedis/:id : delete the "id" hakedis.
     *
     * @param id the id of the hakedis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hakedis/{id}")
    public ResponseEntity<Void> deleteHakedis(@PathVariable Long id) {
        log.debug("REST request to delete Hakedis : {}", id);
        hakedisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

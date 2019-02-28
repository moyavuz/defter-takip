package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.service.UnvanService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.UnvanCriteria;
import com.yavuzturtelekom.service.UnvanQueryService;
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
 * REST controller for managing Unvan.
 */
@RestController
@RequestMapping("/api")
public class UnvanResource {

    private final Logger log = LoggerFactory.getLogger(UnvanResource.class);

    private static final String ENTITY_NAME = "unvan";

    private final UnvanService unvanService;

    private final UnvanQueryService unvanQueryService;

    public UnvanResource(UnvanService unvanService, UnvanQueryService unvanQueryService) {
        this.unvanService = unvanService;
        this.unvanQueryService = unvanQueryService;
    }

    /**
     * POST  /unvans : Create a new unvan.
     *
     * @param unvan the unvan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unvan, or with status 400 (Bad Request) if the unvan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unvans")
    public ResponseEntity<Unvan> createUnvan(@Valid @RequestBody Unvan unvan) throws URISyntaxException {
        log.debug("REST request to save Unvan : {}", unvan);
        if (unvan.getId() != null) {
            throw new BadRequestAlertException("A new unvan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unvan result = unvanService.save(unvan);
        return ResponseEntity.created(new URI("/api/unvans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unvans : Updates an existing unvan.
     *
     * @param unvan the unvan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unvan,
     * or with status 400 (Bad Request) if the unvan is not valid,
     * or with status 500 (Internal Server Error) if the unvan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unvans")
    public ResponseEntity<Unvan> updateUnvan(@Valid @RequestBody Unvan unvan) throws URISyntaxException {
        log.debug("REST request to update Unvan : {}", unvan);
        if (unvan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Unvan result = unvanService.save(unvan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unvan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unvans : get all the unvans.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of unvans in body
     */
    @GetMapping("/unvans")
    public ResponseEntity<List<Unvan>> getAllUnvans(UnvanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Unvans by criteria: {}", criteria);
        Page<Unvan> page = unvanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unvans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /unvans/count : count all the unvans.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/unvans/count")
    public ResponseEntity<Long> countUnvans(UnvanCriteria criteria) {
        log.debug("REST request to count Unvans by criteria: {}", criteria);
        return ResponseEntity.ok().body(unvanQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /unvans/:id : get the "id" unvan.
     *
     * @param id the id of the unvan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unvan, or with status 404 (Not Found)
     */
    @GetMapping("/unvans/{id}")
    public ResponseEntity<Unvan> getUnvan(@PathVariable Long id) {
        log.debug("REST request to get Unvan : {}", id);
        Optional<Unvan> unvan = unvanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unvan);
    }

    /**
     * DELETE  /unvans/:id : delete the "id" unvan.
     *
     * @param id the id of the unvan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unvans/{id}")
    public ResponseEntity<Void> deleteUnvan(@PathVariable Long id) {
        log.debug("REST request to delete Unvan : {}", id);
        unvanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.ProjeTuru;
import com.yavuzturtelekom.service.ProjeTuruService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.ProjeTuruCriteria;
import com.yavuzturtelekom.service.ProjeTuruQueryService;
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
 * REST controller for managing ProjeTuru.
 */
@RestController
@RequestMapping("/api")
public class ProjeTuruResource {

    private final Logger log = LoggerFactory.getLogger(ProjeTuruResource.class);

    private static final String ENTITY_NAME = "projeTuru";

    private final ProjeTuruService projeTuruService;

    private final ProjeTuruQueryService projeTuruQueryService;

    public ProjeTuruResource(ProjeTuruService projeTuruService, ProjeTuruQueryService projeTuruQueryService) {
        this.projeTuruService = projeTuruService;
        this.projeTuruQueryService = projeTuruQueryService;
    }

    /**
     * POST  /proje-turus : Create a new projeTuru.
     *
     * @param projeTuru the projeTuru to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projeTuru, or with status 400 (Bad Request) if the projeTuru has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proje-turus")
    public ResponseEntity<ProjeTuru> createProjeTuru(@Valid @RequestBody ProjeTuru projeTuru) throws URISyntaxException {
        log.debug("REST request to save ProjeTuru : {}", projeTuru);
        if (projeTuru.getId() != null) {
            throw new BadRequestAlertException("A new projeTuru cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjeTuru result = projeTuruService.save(projeTuru);
        return ResponseEntity.created(new URI("/api/proje-turus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proje-turus : Updates an existing projeTuru.
     *
     * @param projeTuru the projeTuru to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projeTuru,
     * or with status 400 (Bad Request) if the projeTuru is not valid,
     * or with status 500 (Internal Server Error) if the projeTuru couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proje-turus")
    public ResponseEntity<ProjeTuru> updateProjeTuru(@Valid @RequestBody ProjeTuru projeTuru) throws URISyntaxException {
        log.debug("REST request to update ProjeTuru : {}", projeTuru);
        if (projeTuru.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjeTuru result = projeTuruService.save(projeTuru);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projeTuru.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proje-turus : get all the projeTurus.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of projeTurus in body
     */
    @GetMapping("/proje-turus")
    public ResponseEntity<List<ProjeTuru>> getAllProjeTurus(ProjeTuruCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProjeTurus by criteria: {}", criteria);
        Page<ProjeTuru> page = projeTuruQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proje-turus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /proje-turus/count : count all the projeTurus.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/proje-turus/count")
    public ResponseEntity<Long> countProjeTurus(ProjeTuruCriteria criteria) {
        log.debug("REST request to count ProjeTurus by criteria: {}", criteria);
        return ResponseEntity.ok().body(projeTuruQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /proje-turus/:id : get the "id" projeTuru.
     *
     * @param id the id of the projeTuru to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projeTuru, or with status 404 (Not Found)
     */
    @GetMapping("/proje-turus/{id}")
    public ResponseEntity<ProjeTuru> getProjeTuru(@PathVariable Long id) {
        log.debug("REST request to get ProjeTuru : {}", id);
        Optional<ProjeTuru> projeTuru = projeTuruService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projeTuru);
    }

    /**
     * DELETE  /proje-turus/:id : delete the "id" projeTuru.
     *
     * @param id the id of the projeTuru to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proje-turus/{id}")
    public ResponseEntity<Void> deleteProjeTuru(@PathVariable Long id) {
        log.debug("REST request to delete ProjeTuru : {}", id);
        projeTuruService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

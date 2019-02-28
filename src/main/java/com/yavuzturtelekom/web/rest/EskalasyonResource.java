package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Eskalasyon;
import com.yavuzturtelekom.service.EskalasyonService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.service.dto.EskalasyonCriteria;
import com.yavuzturtelekom.service.EskalasyonQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Eskalasyon.
 */
@RestController
@RequestMapping("/api")
public class EskalasyonResource {

    private final Logger log = LoggerFactory.getLogger(EskalasyonResource.class);

    private static final String ENTITY_NAME = "eskalasyon";

    private final EskalasyonService eskalasyonService;

    private final EskalasyonQueryService eskalasyonQueryService;

    public EskalasyonResource(EskalasyonService eskalasyonService, EskalasyonQueryService eskalasyonQueryService) {
        this.eskalasyonService = eskalasyonService;
        this.eskalasyonQueryService = eskalasyonQueryService;
    }

    /**
     * POST  /eskalasyons : Create a new eskalasyon.
     *
     * @param eskalasyon the eskalasyon to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eskalasyon, or with status 400 (Bad Request) if the eskalasyon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eskalasyons")
    public ResponseEntity<Eskalasyon> createEskalasyon(@Valid @RequestBody Eskalasyon eskalasyon) throws URISyntaxException {
        log.debug("REST request to save Eskalasyon : {}", eskalasyon);
        if (eskalasyon.getId() != null) {
            throw new BadRequestAlertException("A new eskalasyon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Eskalasyon result = eskalasyonService.save(eskalasyon);
        return ResponseEntity.created(new URI("/api/eskalasyons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eskalasyons : Updates an existing eskalasyon.
     *
     * @param eskalasyon the eskalasyon to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eskalasyon,
     * or with status 400 (Bad Request) if the eskalasyon is not valid,
     * or with status 500 (Internal Server Error) if the eskalasyon couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eskalasyons")
    public ResponseEntity<Eskalasyon> updateEskalasyon(@Valid @RequestBody Eskalasyon eskalasyon) throws URISyntaxException {
        log.debug("REST request to update Eskalasyon : {}", eskalasyon);
        if (eskalasyon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Eskalasyon result = eskalasyonService.save(eskalasyon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eskalasyon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eskalasyons : get all the eskalasyons.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of eskalasyons in body
     */
    @GetMapping("/eskalasyons")
    public ResponseEntity<List<Eskalasyon>> getAllEskalasyons(EskalasyonCriteria criteria) {
        log.debug("REST request to get Eskalasyons by criteria: {}", criteria);
        List<Eskalasyon> entityList = eskalasyonQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /eskalasyons/count : count all the eskalasyons.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/eskalasyons/count")
    public ResponseEntity<Long> countEskalasyons(EskalasyonCriteria criteria) {
        log.debug("REST request to count Eskalasyons by criteria: {}", criteria);
        return ResponseEntity.ok().body(eskalasyonQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /eskalasyons/:id : get the "id" eskalasyon.
     *
     * @param id the id of the eskalasyon to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eskalasyon, or with status 404 (Not Found)
     */
    @GetMapping("/eskalasyons/{id}")
    public ResponseEntity<Eskalasyon> getEskalasyon(@PathVariable Long id) {
        log.debug("REST request to get Eskalasyon : {}", id);
        Optional<Eskalasyon> eskalasyon = eskalasyonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eskalasyon);
    }

    /**
     * DELETE  /eskalasyons/:id : delete the "id" eskalasyon.
     *
     * @param id the id of the eskalasyon to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eskalasyons/{id}")
    public ResponseEntity<Void> deleteEskalasyon(@PathVariable Long id) {
        log.debug("REST request to delete Eskalasyon : {}", id);
        eskalasyonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

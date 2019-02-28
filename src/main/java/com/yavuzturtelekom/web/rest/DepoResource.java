package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Depo;
import com.yavuzturtelekom.service.DepoService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.service.dto.DepoCriteria;
import com.yavuzturtelekom.service.DepoQueryService;
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
 * REST controller for managing Depo.
 */
@RestController
@RequestMapping("/api")
public class DepoResource {

    private final Logger log = LoggerFactory.getLogger(DepoResource.class);

    private static final String ENTITY_NAME = "depo";

    private final DepoService depoService;

    private final DepoQueryService depoQueryService;

    public DepoResource(DepoService depoService, DepoQueryService depoQueryService) {
        this.depoService = depoService;
        this.depoQueryService = depoQueryService;
    }

    /**
     * POST  /depos : Create a new depo.
     *
     * @param depo the depo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depo, or with status 400 (Bad Request) if the depo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/depos")
    public ResponseEntity<Depo> createDepo(@Valid @RequestBody Depo depo) throws URISyntaxException {
        log.debug("REST request to save Depo : {}", depo);
        if (depo.getId() != null) {
            throw new BadRequestAlertException("A new depo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Depo result = depoService.save(depo);
        return ResponseEntity.created(new URI("/api/depos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /depos : Updates an existing depo.
     *
     * @param depo the depo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depo,
     * or with status 400 (Bad Request) if the depo is not valid,
     * or with status 500 (Internal Server Error) if the depo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/depos")
    public ResponseEntity<Depo> updateDepo(@Valid @RequestBody Depo depo) throws URISyntaxException {
        log.debug("REST request to update Depo : {}", depo);
        if (depo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Depo result = depoService.save(depo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /depos : get all the depos.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of depos in body
     */
    @GetMapping("/depos")
    public ResponseEntity<List<Depo>> getAllDepos(DepoCriteria criteria) {
        log.debug("REST request to get Depos by criteria: {}", criteria);
        List<Depo> entityList = depoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /depos/count : count all the depos.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/depos/count")
    public ResponseEntity<Long> countDepos(DepoCriteria criteria) {
        log.debug("REST request to count Depos by criteria: {}", criteria);
        return ResponseEntity.ok().body(depoQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /depos/:id : get the "id" depo.
     *
     * @param id the id of the depo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depo, or with status 404 (Not Found)
     */
    @GetMapping("/depos/{id}")
    public ResponseEntity<Depo> getDepo(@PathVariable Long id) {
        log.debug("REST request to get Depo : {}", id);
        Optional<Depo> depo = depoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depo);
    }

    /**
     * DELETE  /depos/:id : delete the "id" depo.
     *
     * @param id the id of the depo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/depos/{id}")
    public ResponseEntity<Void> deleteDepo(@PathVariable Long id) {
        log.debug("REST request to delete Depo : {}", id);
        depoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

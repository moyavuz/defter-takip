package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Bolge;
import com.yavuzturtelekom.service.BolgeService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
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
 * REST controller for managing Bolge.
 */
@RestController
@RequestMapping("/api")
public class BolgeResource {

    private final Logger log = LoggerFactory.getLogger(BolgeResource.class);

    private static final String ENTITY_NAME = "bolge";

    private final BolgeService bolgeService;

    public BolgeResource(BolgeService bolgeService) {
        this.bolgeService = bolgeService;
    }

    /**
     * POST  /bolges : Create a new bolge.
     *
     * @param bolge the bolge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bolge, or with status 400 (Bad Request) if the bolge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bolges")
    public ResponseEntity<Bolge> createBolge(@Valid @RequestBody Bolge bolge) throws URISyntaxException {
        log.debug("REST request to save Bolge : {}", bolge);
        if (bolge.getId() != null) {
            throw new BadRequestAlertException("A new bolge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bolge result = bolgeService.save(bolge);
        return ResponseEntity.created(new URI("/api/bolges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bolges : Updates an existing bolge.
     *
     * @param bolge the bolge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bolge,
     * or with status 400 (Bad Request) if the bolge is not valid,
     * or with status 500 (Internal Server Error) if the bolge couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bolges")
    public ResponseEntity<Bolge> updateBolge(@Valid @RequestBody Bolge bolge) throws URISyntaxException {
        log.debug("REST request to update Bolge : {}", bolge);
        if (bolge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bolge result = bolgeService.save(bolge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bolge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bolges : get all the bolges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bolges in body
     */
    @GetMapping("/bolges")
    public List<Bolge> getAllBolges() {
        log.debug("REST request to get all Bolges");
        return bolgeService.findAll();
    }

    /**
     * GET  /bolges/:id : get the "id" bolge.
     *
     * @param id the id of the bolge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bolge, or with status 404 (Not Found)
     */
    @GetMapping("/bolges/{id}")
    public ResponseEntity<Bolge> getBolge(@PathVariable Long id) {
        log.debug("REST request to get Bolge : {}", id);
        Optional<Bolge> bolge = bolgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bolge);
    }

    /**
     * DELETE  /bolges/:id : delete the "id" bolge.
     *
     * @param id the id of the bolge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bolges/{id}")
    public ResponseEntity<Void> deleteBolge(@PathVariable Long id) {
        log.debug("REST request to delete Bolge : {}", id);
        bolgeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

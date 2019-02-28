package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Marka;
import com.yavuzturtelekom.service.MarkaService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.service.dto.MarkaCriteria;
import com.yavuzturtelekom.service.MarkaQueryService;
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
 * REST controller for managing Marka.
 */
@RestController
@RequestMapping("/api")
public class MarkaResource {

    private final Logger log = LoggerFactory.getLogger(MarkaResource.class);

    private static final String ENTITY_NAME = "marka";

    private final MarkaService markaService;

    private final MarkaQueryService markaQueryService;

    public MarkaResource(MarkaService markaService, MarkaQueryService markaQueryService) {
        this.markaService = markaService;
        this.markaQueryService = markaQueryService;
    }

    /**
     * POST  /markas : Create a new marka.
     *
     * @param marka the marka to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marka, or with status 400 (Bad Request) if the marka has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/markas")
    public ResponseEntity<Marka> createMarka(@Valid @RequestBody Marka marka) throws URISyntaxException {
        log.debug("REST request to save Marka : {}", marka);
        if (marka.getId() != null) {
            throw new BadRequestAlertException("A new marka cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Marka result = markaService.save(marka);
        return ResponseEntity.created(new URI("/api/markas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /markas : Updates an existing marka.
     *
     * @param marka the marka to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marka,
     * or with status 400 (Bad Request) if the marka is not valid,
     * or with status 500 (Internal Server Error) if the marka couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/markas")
    public ResponseEntity<Marka> updateMarka(@Valid @RequestBody Marka marka) throws URISyntaxException {
        log.debug("REST request to update Marka : {}", marka);
        if (marka.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Marka result = markaService.save(marka);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marka.getId().toString()))
            .body(result);
    }

    /**
     * GET  /markas : get all the markas.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of markas in body
     */
    @GetMapping("/markas")
    public ResponseEntity<List<Marka>> getAllMarkas(MarkaCriteria criteria) {
        log.debug("REST request to get Markas by criteria: {}", criteria);
        List<Marka> entityList = markaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /markas/count : count all the markas.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/markas/count")
    public ResponseEntity<Long> countMarkas(MarkaCriteria criteria) {
        log.debug("REST request to count Markas by criteria: {}", criteria);
        return ResponseEntity.ok().body(markaQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /markas/:id : get the "id" marka.
     *
     * @param id the id of the marka to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marka, or with status 404 (Not Found)
     */
    @GetMapping("/markas/{id}")
    public ResponseEntity<Marka> getMarka(@PathVariable Long id) {
        log.debug("REST request to get Marka : {}", id);
        Optional<Marka> marka = markaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(marka);
    }

    /**
     * DELETE  /markas/:id : delete the "id" marka.
     *
     * @param id the id of the marka to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/markas/{id}")
    public ResponseEntity<Void> deleteMarka(@PathVariable Long id) {
        log.debug("REST request to delete Marka : {}", id);
        markaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

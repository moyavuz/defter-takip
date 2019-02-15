package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.service.BirimService;
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
 * REST controller for managing Birim.
 */
@RestController
@RequestMapping("/api")
public class BirimResource {

    private final Logger log = LoggerFactory.getLogger(BirimResource.class);

    private static final String ENTITY_NAME = "birim";

    private final BirimService birimService;

    public BirimResource(BirimService birimService) {
        this.birimService = birimService;
    }

    /**
     * POST  /birims : Create a new birim.
     *
     * @param birim the birim to create
     * @return the ResponseEntity with status 201 (Created) and with body the new birim, or with status 400 (Bad Request) if the birim has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/birims")
    public ResponseEntity<Birim> createBirim(@Valid @RequestBody Birim birim) throws URISyntaxException {
        log.debug("REST request to save Birim : {}", birim);
        if (birim.getId() != null) {
            throw new BadRequestAlertException("A new birim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Birim result = birimService.save(birim);
        return ResponseEntity.created(new URI("/api/birims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /birims : Updates an existing birim.
     *
     * @param birim the birim to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated birim,
     * or with status 400 (Bad Request) if the birim is not valid,
     * or with status 500 (Internal Server Error) if the birim couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/birims")
    public ResponseEntity<Birim> updateBirim(@Valid @RequestBody Birim birim) throws URISyntaxException {
        log.debug("REST request to update Birim : {}", birim);
        if (birim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Birim result = birimService.save(birim);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, birim.getId().toString()))
            .body(result);
    }

    /**
     * GET  /birims : get all the birims.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of birims in body
     */
    @GetMapping("/birims")
    public List<Birim> getAllBirims() {
        log.debug("REST request to get all Birims");
        return birimService.findAll();
    }

    /**
     * GET  /birims/:id : get the "id" birim.
     *
     * @param id the id of the birim to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the birim, or with status 404 (Not Found)
     */
    @GetMapping("/birims/{id}")
    public ResponseEntity<Birim> getBirim(@PathVariable Long id) {
        log.debug("REST request to get Birim : {}", id);
        Optional<Birim> birim = birimService.findOne(id);
        return ResponseUtil.wrapOrNotFound(birim);
    }

    /**
     * DELETE  /birims/:id : delete the "id" birim.
     *
     * @param id the id of the birim to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/birims/{id}")
    public ResponseEntity<Void> deleteBirim(@PathVariable Long id) {
        log.debug("REST request to delete Birim : {}", id);
        birimService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

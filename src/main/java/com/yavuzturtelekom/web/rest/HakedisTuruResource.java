package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.HakedisTuru;
import com.yavuzturtelekom.service.HakedisTuruService;
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
 * REST controller for managing HakedisTuru.
 */
@RestController
@RequestMapping("/api")
public class HakedisTuruResource {

    private final Logger log = LoggerFactory.getLogger(HakedisTuruResource.class);

    private static final String ENTITY_NAME = "hakedisTuru";

    private final HakedisTuruService hakedisTuruService;

    public HakedisTuruResource(HakedisTuruService hakedisTuruService) {
        this.hakedisTuruService = hakedisTuruService;
    }

    /**
     * POST  /hakedis-turus : Create a new hakedisTuru.
     *
     * @param hakedisTuru the hakedisTuru to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hakedisTuru, or with status 400 (Bad Request) if the hakedisTuru has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hakedis-turus")
    public ResponseEntity<HakedisTuru> createHakedisTuru(@Valid @RequestBody HakedisTuru hakedisTuru) throws URISyntaxException {
        log.debug("REST request to save HakedisTuru : {}", hakedisTuru);
        if (hakedisTuru.getId() != null) {
            throw new BadRequestAlertException("A new hakedisTuru cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HakedisTuru result = hakedisTuruService.save(hakedisTuru);
        return ResponseEntity.created(new URI("/api/hakedis-turus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hakedis-turus : Updates an existing hakedisTuru.
     *
     * @param hakedisTuru the hakedisTuru to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hakedisTuru,
     * or with status 400 (Bad Request) if the hakedisTuru is not valid,
     * or with status 500 (Internal Server Error) if the hakedisTuru couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hakedis-turus")
    public ResponseEntity<HakedisTuru> updateHakedisTuru(@Valid @RequestBody HakedisTuru hakedisTuru) throws URISyntaxException {
        log.debug("REST request to update HakedisTuru : {}", hakedisTuru);
        if (hakedisTuru.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HakedisTuru result = hakedisTuruService.save(hakedisTuru);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hakedisTuru.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hakedis-turus : get all the hakedisTurus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hakedisTurus in body
     */
    @GetMapping("/hakedis-turus")
    public List<HakedisTuru> getAllHakedisTurus() {
        log.debug("REST request to get all HakedisTurus");
        return hakedisTuruService.findAll();
    }

    /**
     * GET  /hakedis-turus/:id : get the "id" hakedisTuru.
     *
     * @param id the id of the hakedisTuru to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hakedisTuru, or with status 404 (Not Found)
     */
    @GetMapping("/hakedis-turus/{id}")
    public ResponseEntity<HakedisTuru> getHakedisTuru(@PathVariable Long id) {
        log.debug("REST request to get HakedisTuru : {}", id);
        Optional<HakedisTuru> hakedisTuru = hakedisTuruService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hakedisTuru);
    }

    /**
     * DELETE  /hakedis-turus/:id : delete the "id" hakedisTuru.
     *
     * @param id the id of the hakedisTuru to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hakedis-turus/{id}")
    public ResponseEntity<Void> deleteHakedisTuru(@PathVariable Long id) {
        log.debug("REST request to delete HakedisTuru : {}", id);
        hakedisTuruService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

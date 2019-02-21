package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.DovizKur;
import com.yavuzturtelekom.service.DovizKurService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DovizKur.
 */
@RestController
@RequestMapping("/api")
public class DovizKurResource {

    private final Logger log = LoggerFactory.getLogger(DovizKurResource.class);

    private static final String ENTITY_NAME = "dovizKur";

    private final DovizKurService dovizKurService;

    public DovizKurResource(DovizKurService dovizKurService) {
        this.dovizKurService = dovizKurService;
    }

    /**
     * POST  /doviz-kurs : Create a new dovizKur.
     *
     * @param dovizKur the dovizKur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dovizKur, or with status 400 (Bad Request) if the dovizKur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doviz-kurs")
    public ResponseEntity<DovizKur> createDovizKur(@RequestBody DovizKur dovizKur) throws URISyntaxException {
        log.debug("REST request to save DovizKur : {}", dovizKur);
        if (dovizKur.getId() != null) {
            throw new BadRequestAlertException("A new dovizKur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DovizKur result = dovizKurService.save(dovizKur);
        return ResponseEntity.created(new URI("/api/doviz-kurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doviz-kurs : Updates an existing dovizKur.
     *
     * @param dovizKur the dovizKur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dovizKur,
     * or with status 400 (Bad Request) if the dovizKur is not valid,
     * or with status 500 (Internal Server Error) if the dovizKur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doviz-kurs")
    public ResponseEntity<DovizKur> updateDovizKur(@RequestBody DovizKur dovizKur) throws URISyntaxException {
        log.debug("REST request to update DovizKur : {}", dovizKur);
        if (dovizKur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DovizKur result = dovizKurService.save(dovizKur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dovizKur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doviz-kurs : get all the dovizKurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dovizKurs in body
     */
    @GetMapping("/doviz-kurs")
    public List<DovizKur> getAllDovizKurs() {
        log.debug("REST request to get all DovizKurs");
        return dovizKurService.findAll();
    }

    /**
     * GET  /doviz-kurs/:id : get the "id" dovizKur.
     *
     * @param id the id of the dovizKur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dovizKur, or with status 404 (Not Found)
     */
    @GetMapping("/doviz-kurs/{id}")
    public ResponseEntity<DovizKur> getDovizKur(@PathVariable Long id) {
        log.debug("REST request to get DovizKur : {}", id);
        Optional<DovizKur> dovizKur = dovizKurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dovizKur);
    }

    /**
     * DELETE  /doviz-kurs/:id : delete the "id" dovizKur.
     *
     * @param id the id of the dovizKur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doviz-kurs/{id}")
    public ResponseEntity<Void> deleteDovizKur(@PathVariable Long id) {
        log.debug("REST request to delete DovizKur : {}", id);
        dovizKurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

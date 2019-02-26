package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.PozGrubu;
import com.yavuzturtelekom.service.PozGrubuService;
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
 * REST controller for managing PozGrubu.
 */
@RestController
@RequestMapping("/api")
public class PozGrubuResource {

    private final Logger log = LoggerFactory.getLogger(PozGrubuResource.class);

    private static final String ENTITY_NAME = "pozGrubu";

    private final PozGrubuService pozGrubuService;

    public PozGrubuResource(PozGrubuService pozGrubuService) {
        this.pozGrubuService = pozGrubuService;
    }

    /**
     * POST  /poz-grubus : Create a new pozGrubu.
     *
     * @param pozGrubu the pozGrubu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pozGrubu, or with status 400 (Bad Request) if the pozGrubu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/poz-grubus")
    public ResponseEntity<PozGrubu> createPozGrubu(@Valid @RequestBody PozGrubu pozGrubu) throws URISyntaxException {
        log.debug("REST request to save PozGrubu : {}", pozGrubu);
        if (pozGrubu.getId() != null) {
            throw new BadRequestAlertException("A new pozGrubu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PozGrubu result = pozGrubuService.save(pozGrubu);
        return ResponseEntity.created(new URI("/api/poz-grubus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /poz-grubus : Updates an existing pozGrubu.
     *
     * @param pozGrubu the pozGrubu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pozGrubu,
     * or with status 400 (Bad Request) if the pozGrubu is not valid,
     * or with status 500 (Internal Server Error) if the pozGrubu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/poz-grubus")
    public ResponseEntity<PozGrubu> updatePozGrubu(@Valid @RequestBody PozGrubu pozGrubu) throws URISyntaxException {
        log.debug("REST request to update PozGrubu : {}", pozGrubu);
        if (pozGrubu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PozGrubu result = pozGrubuService.save(pozGrubu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pozGrubu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /poz-grubus : get all the pozGrubus.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of pozGrubus in body
     */
    @GetMapping("/poz-grubus")
    public List<PozGrubu> getAllPozGrubus(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PozGrubus");
        return pozGrubuService.findAll();
    }

    /**
     * GET  /poz-grubus/:id : get the "id" pozGrubu.
     *
     * @param id the id of the pozGrubu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pozGrubu, or with status 404 (Not Found)
     */
    @GetMapping("/poz-grubus/{id}")
    public ResponseEntity<PozGrubu> getPozGrubu(@PathVariable Long id) {
        log.debug("REST request to get PozGrubu : {}", id);
        Optional<PozGrubu> pozGrubu = pozGrubuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pozGrubu);
    }

    /**
     * DELETE  /poz-grubus/:id : delete the "id" pozGrubu.
     *
     * @param id the id of the pozGrubu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/poz-grubus/{id}")
    public ResponseEntity<Void> deletePozGrubu(@PathVariable Long id) {
        log.debug("REST request to delete PozGrubu : {}", id);
        pozGrubuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

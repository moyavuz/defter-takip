package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.MalzemeGrubu;
import com.yavuzturtelekom.service.MalzemeGrubuService;
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
 * REST controller for managing MalzemeGrubu.
 */
@RestController
@RequestMapping("/api")
public class MalzemeGrubuResource {

    private final Logger log = LoggerFactory.getLogger(MalzemeGrubuResource.class);

    private static final String ENTITY_NAME = "malzemeGrubu";

    private final MalzemeGrubuService malzemeGrubuService;

    public MalzemeGrubuResource(MalzemeGrubuService malzemeGrubuService) {
        this.malzemeGrubuService = malzemeGrubuService;
    }

    /**
     * POST  /malzeme-grubus : Create a new malzemeGrubu.
     *
     * @param malzemeGrubu the malzemeGrubu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new malzemeGrubu, or with status 400 (Bad Request) if the malzemeGrubu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/malzeme-grubus")
    public ResponseEntity<MalzemeGrubu> createMalzemeGrubu(@Valid @RequestBody MalzemeGrubu malzemeGrubu) throws URISyntaxException {
        log.debug("REST request to save MalzemeGrubu : {}", malzemeGrubu);
        if (malzemeGrubu.getId() != null) {
            throw new BadRequestAlertException("A new malzemeGrubu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MalzemeGrubu result = malzemeGrubuService.save(malzemeGrubu);
        return ResponseEntity.created(new URI("/api/malzeme-grubus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /malzeme-grubus : Updates an existing malzemeGrubu.
     *
     * @param malzemeGrubu the malzemeGrubu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated malzemeGrubu,
     * or with status 400 (Bad Request) if the malzemeGrubu is not valid,
     * or with status 500 (Internal Server Error) if the malzemeGrubu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/malzeme-grubus")
    public ResponseEntity<MalzemeGrubu> updateMalzemeGrubu(@Valid @RequestBody MalzemeGrubu malzemeGrubu) throws URISyntaxException {
        log.debug("REST request to update MalzemeGrubu : {}", malzemeGrubu);
        if (malzemeGrubu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MalzemeGrubu result = malzemeGrubuService.save(malzemeGrubu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, malzemeGrubu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /malzeme-grubus : get all the malzemeGrubus.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of malzemeGrubus in body
     */
    @GetMapping("/malzeme-grubus")
    public List<MalzemeGrubu> getAllMalzemeGrubus(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all MalzemeGrubus");
        return malzemeGrubuService.findAll();
    }

    /**
     * GET  /malzeme-grubus/:id : get the "id" malzemeGrubu.
     *
     * @param id the id of the malzemeGrubu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the malzemeGrubu, or with status 404 (Not Found)
     */
    @GetMapping("/malzeme-grubus/{id}")
    public ResponseEntity<MalzemeGrubu> getMalzemeGrubu(@PathVariable Long id) {
        log.debug("REST request to get MalzemeGrubu : {}", id);
        Optional<MalzemeGrubu> malzemeGrubu = malzemeGrubuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(malzemeGrubu);
    }

    /**
     * DELETE  /malzeme-grubus/:id : delete the "id" malzemeGrubu.
     *
     * @param id the id of the malzemeGrubu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/malzeme-grubus/{id}")
    public ResponseEntity<Void> deleteMalzemeGrubu(@PathVariable Long id) {
        log.debug("REST request to delete MalzemeGrubu : {}", id);
        malzemeGrubuService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

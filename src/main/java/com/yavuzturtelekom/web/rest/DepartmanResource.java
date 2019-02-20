package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Departman;
import com.yavuzturtelekom.service.DepartmanService;
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
 * REST controller for managing Departman.
 */
@RestController
@RequestMapping("/api")
public class DepartmanResource {

    private final Logger log = LoggerFactory.getLogger(DepartmanResource.class);

    private static final String ENTITY_NAME = "departman";

    private final DepartmanService departmanService;

    public DepartmanResource(DepartmanService departmanService) {
        this.departmanService = departmanService;
    }

    /**
     * POST  /departmen : Create a new departman.
     *
     * @param departman the departman to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departman, or with status 400 (Bad Request) if the departman has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departmen")
    public ResponseEntity<Departman> createDepartman(@Valid @RequestBody Departman departman) throws URISyntaxException {
        log.debug("REST request to save Departman : {}", departman);
        if (departman.getId() != null) {
            throw new BadRequestAlertException("A new departman cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departman result = departmanService.save(departman);
        return ResponseEntity.created(new URI("/api/departmen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departmen : Updates an existing departman.
     *
     * @param departman the departman to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departman,
     * or with status 400 (Bad Request) if the departman is not valid,
     * or with status 500 (Internal Server Error) if the departman couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departmen")
    public ResponseEntity<Departman> updateDepartman(@Valid @RequestBody Departman departman) throws URISyntaxException {
        log.debug("REST request to update Departman : {}", departman);
        if (departman.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Departman result = departmanService.save(departman);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departman.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departmen : get all the departmen.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of departmen in body
     */
    @GetMapping("/departmen")
    public List<Departman> getAllDepartmen() {
        log.debug("REST request to get all Departmen");
        return departmanService.findAll();
    }

    /**
     * GET  /departmen/:id : get the "id" departman.
     *
     * @param id the id of the departman to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departman, or with status 404 (Not Found)
     */
    @GetMapping("/departmen/{id}")
    public ResponseEntity<Departman> getDepartman(@PathVariable Long id) {
        log.debug("REST request to get Departman : {}", id);
        Optional<Departman> departman = departmanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departman);
    }

    /**
     * DELETE  /departmen/:id : delete the "id" departman.
     *
     * @param id the id of the departman to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departmen/{id}")
    public ResponseEntity<Void> deleteDepartman(@PathVariable Long id) {
        log.debug("REST request to delete Departman : {}", id);
        departmanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

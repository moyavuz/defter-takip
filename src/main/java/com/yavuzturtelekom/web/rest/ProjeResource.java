package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.service.ProjeService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Proje.
 */
@RestController
@RequestMapping("/api")
public class ProjeResource {

    private final Logger log = LoggerFactory.getLogger(ProjeResource.class);

    private static final String ENTITY_NAME = "proje";

    private final ProjeService projeService;

    public ProjeResource(ProjeService projeService) {
        this.projeService = projeService;
    }

    /**
     * POST  /projes : Create a new proje.
     *
     * @param proje the proje to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proje, or with status 400 (Bad Request) if the proje has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projes")
    public ResponseEntity<Proje> createProje(@RequestBody Proje proje) throws URISyntaxException {
        log.debug("REST request to save Proje : {}", proje);
        if (proje.getId() != null) {
            throw new BadRequestAlertException("A new proje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proje result = projeService.save(proje);
        return ResponseEntity.created(new URI("/api/projes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projes : Updates an existing proje.
     *
     * @param proje the proje to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proje,
     * or with status 400 (Bad Request) if the proje is not valid,
     * or with status 500 (Internal Server Error) if the proje couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projes")
    public ResponseEntity<Proje> updateProje(@RequestBody Proje proje) throws URISyntaxException {
        log.debug("REST request to update Proje : {}", proje);
        if (proje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Proje result = projeService.save(proje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proje.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projes : get all the projes.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of projes in body
     */
    @GetMapping("/projes")
    public ResponseEntity<List<Proje>> getAllProjes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Projes");
        Page<Proje> page;
        if (eagerload) {
            page = projeService.findAllWithEagerRelationships(pageable);
        } else {
            page = projeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/projes?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /projes/:id : get the "id" proje.
     *
     * @param id the id of the proje to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proje, or with status 404 (Not Found)
     */
    @GetMapping("/projes/{id}")
    public ResponseEntity<Proje> getProje(@PathVariable Long id) {
        log.debug("REST request to get Proje : {}", id);
        Optional<Proje> proje = projeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proje);
    }

    /**
     * DELETE  /projes/:id : delete the "id" proje.
     *
     * @param id the id of the proje to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projes/{id}")
    public ResponseEntity<Void> deleteProje(@PathVariable Long id) {
        log.debug("REST request to delete Proje : {}", id);
        projeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

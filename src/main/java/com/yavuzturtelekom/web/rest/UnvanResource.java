package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.service.UnvanService;
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
 * REST controller for managing Unvan.
 */
@RestController
@RequestMapping("/api")
public class UnvanResource {

    private final Logger log = LoggerFactory.getLogger(UnvanResource.class);

    private static final String ENTITY_NAME = "unvan";

    private final UnvanService unvanService;

    public UnvanResource(UnvanService unvanService) {
        this.unvanService = unvanService;
    }

    /**
     * POST  /unvans : Create a new unvan.
     *
     * @param unvan the unvan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unvan, or with status 400 (Bad Request) if the unvan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unvans")
    public ResponseEntity<Unvan> createUnvan(@Valid @RequestBody Unvan unvan) throws URISyntaxException {
        log.debug("REST request to save Unvan : {}", unvan);
        if (unvan.getId() != null) {
            throw new BadRequestAlertException("A new unvan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Unvan result = unvanService.save(unvan);
        return ResponseEntity.created(new URI("/api/unvans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unvans : Updates an existing unvan.
     *
     * @param unvan the unvan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unvan,
     * or with status 400 (Bad Request) if the unvan is not valid,
     * or with status 500 (Internal Server Error) if the unvan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unvans")
    public ResponseEntity<Unvan> updateUnvan(@Valid @RequestBody Unvan unvan) throws URISyntaxException {
        log.debug("REST request to update Unvan : {}", unvan);
        if (unvan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Unvan result = unvanService.save(unvan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unvan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unvans : get all the unvans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unvans in body
     */
    @GetMapping("/unvans")
    public List<Unvan> getAllUnvans() {
        log.debug("REST request to get all Unvans");
        return unvanService.findAll();
    }

    /**
     * GET  /unvans/:id : get the "id" unvan.
     *
     * @param id the id of the unvan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unvan, or with status 404 (Not Found)
     */
    @GetMapping("/unvans/{id}")
    public ResponseEntity<Unvan> getUnvan(@PathVariable Long id) {
        log.debug("REST request to get Unvan : {}", id);
        Optional<Unvan> unvan = unvanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unvan);
    }

    /**
     * DELETE  /unvans/:id : delete the "id" unvan.
     *
     * @param id the id of the unvan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unvans/{id}")
    public ResponseEntity<Void> deleteUnvan(@PathVariable Long id) {
        log.debug("REST request to delete Unvan : {}", id);
        unvanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

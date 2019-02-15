package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.service.EkipService;
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
 * REST controller for managing Ekip.
 */
@RestController
@RequestMapping("/api")
public class EkipResource {

    private final Logger log = LoggerFactory.getLogger(EkipResource.class);

    private static final String ENTITY_NAME = "ekip";

    private final EkipService ekipService;

    public EkipResource(EkipService ekipService) {
        this.ekipService = ekipService;
    }

    /**
     * POST  /ekips : Create a new ekip.
     *
     * @param ekip the ekip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ekip, or with status 400 (Bad Request) if the ekip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ekips")
    public ResponseEntity<Ekip> createEkip(@Valid @RequestBody Ekip ekip) throws URISyntaxException {
        log.debug("REST request to save Ekip : {}", ekip);
        if (ekip.getId() != null) {
            throw new BadRequestAlertException("A new ekip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ekip result = ekipService.save(ekip);
        return ResponseEntity.created(new URI("/api/ekips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ekips : Updates an existing ekip.
     *
     * @param ekip the ekip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ekip,
     * or with status 400 (Bad Request) if the ekip is not valid,
     * or with status 500 (Internal Server Error) if the ekip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ekips")
    public ResponseEntity<Ekip> updateEkip(@Valid @RequestBody Ekip ekip) throws URISyntaxException {
        log.debug("REST request to update Ekip : {}", ekip);
        if (ekip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ekip result = ekipService.save(ekip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ekip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ekips : get all the ekips.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of ekips in body
     */
    @GetMapping("/ekips")
    public List<Ekip> getAllEkips(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Ekips");
        return ekipService.findAll();
    }

    /**
     * GET  /ekips/:id : get the "id" ekip.
     *
     * @param id the id of the ekip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ekip, or with status 404 (Not Found)
     */
    @GetMapping("/ekips/{id}")
    public ResponseEntity<Ekip> getEkip(@PathVariable Long id) {
        log.debug("REST request to get Ekip : {}", id);
        Optional<Ekip> ekip = ekipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ekip);
    }

    /**
     * DELETE  /ekips/:id : delete the "id" ekip.
     *
     * @param id the id of the ekip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ekips/{id}")
    public ResponseEntity<Void> deleteEkip(@PathVariable Long id) {
        log.debug("REST request to delete Ekip : {}", id);
        ekipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

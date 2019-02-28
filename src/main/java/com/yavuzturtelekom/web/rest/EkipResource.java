package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.service.EkipService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.EkipCriteria;
import com.yavuzturtelekom.service.EkipQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    private final EkipQueryService ekipQueryService;

    public EkipResource(EkipService ekipService, EkipQueryService ekipQueryService) {
        this.ekipService = ekipService;
        this.ekipQueryService = ekipQueryService;
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
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of ekips in body
     */
    @GetMapping("/ekips")
    public ResponseEntity<List<Ekip>> getAllEkips(EkipCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ekips by criteria: {}", criteria);
        Page<Ekip> page = ekipQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ekips");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /ekips/count : count all the ekips.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/ekips/count")
    public ResponseEntity<Long> countEkips(EkipCriteria criteria) {
        log.debug("REST request to count Ekips by criteria: {}", criteria);
        return ResponseEntity.ok().body(ekipQueryService.countByCriteria(criteria));
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

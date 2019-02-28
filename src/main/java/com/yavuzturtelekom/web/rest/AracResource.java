package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Arac;
import com.yavuzturtelekom.service.AracService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.AracCriteria;
import com.yavuzturtelekom.service.AracQueryService;
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
 * REST controller for managing Arac.
 */
@RestController
@RequestMapping("/api")
public class AracResource {

    private final Logger log = LoggerFactory.getLogger(AracResource.class);

    private static final String ENTITY_NAME = "arac";

    private final AracService aracService;

    private final AracQueryService aracQueryService;

    public AracResource(AracService aracService, AracQueryService aracQueryService) {
        this.aracService = aracService;
        this.aracQueryService = aracQueryService;
    }

    /**
     * POST  /aracs : Create a new arac.
     *
     * @param arac the arac to create
     * @return the ResponseEntity with status 201 (Created) and with body the new arac, or with status 400 (Bad Request) if the arac has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/aracs")
    public ResponseEntity<Arac> createArac(@RequestBody Arac arac) throws URISyntaxException {
        log.debug("REST request to save Arac : {}", arac);
        if (arac.getId() != null) {
            throw new BadRequestAlertException("A new arac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arac result = aracService.save(arac);
        return ResponseEntity.created(new URI("/api/aracs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aracs : Updates an existing arac.
     *
     * @param arac the arac to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated arac,
     * or with status 400 (Bad Request) if the arac is not valid,
     * or with status 500 (Internal Server Error) if the arac couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/aracs")
    public ResponseEntity<Arac> updateArac(@RequestBody Arac arac) throws URISyntaxException {
        log.debug("REST request to update Arac : {}", arac);
        if (arac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arac result = aracService.save(arac);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, arac.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aracs : get all the aracs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of aracs in body
     */
    @GetMapping("/aracs")
    public ResponseEntity<List<Arac>> getAllAracs(AracCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Aracs by criteria: {}", criteria);
        Page<Arac> page = aracQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aracs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /aracs/count : count all the aracs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/aracs/count")
    public ResponseEntity<Long> countAracs(AracCriteria criteria) {
        log.debug("REST request to count Aracs by criteria: {}", criteria);
        return ResponseEntity.ok().body(aracQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /aracs/:id : get the "id" arac.
     *
     * @param id the id of the arac to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the arac, or with status 404 (Not Found)
     */
    @GetMapping("/aracs/{id}")
    public ResponseEntity<Arac> getArac(@PathVariable Long id) {
        log.debug("REST request to get Arac : {}", id);
        Optional<Arac> arac = aracService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arac);
    }

    /**
     * DELETE  /aracs/:id : delete the "id" arac.
     *
     * @param id the id of the arac to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/aracs/{id}")
    public ResponseEntity<Void> deleteArac(@PathVariable Long id) {
        log.debug("REST request to delete Arac : {}", id);
        aracService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

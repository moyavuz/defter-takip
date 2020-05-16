package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.service.MalzemeService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.MalzemeCriteria;
import com.yavuzturtelekom.service.MalzemeQueryService;
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
 * REST controller for managing Malzeme.
 */
@RestController
@RequestMapping("/api")
public class MalzemeResource {

    private final Logger log = LoggerFactory.getLogger(MalzemeResource.class);

    private static final String ENTITY_NAME = "malzeme";

    private final MalzemeService malzemeService;

    private final MalzemeQueryService malzemeQueryService;

    public MalzemeResource(MalzemeService malzemeService, MalzemeQueryService malzemeQueryService) {
        this.malzemeService = malzemeService;
        this.malzemeQueryService = malzemeQueryService;
    }

    /**
     * POST  /malzemes : Create a new malzeme.
     *
     * @param malzeme the malzeme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new malzeme, or with status 400 (Bad Request) if the malzeme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/malzemes")
    public ResponseEntity<Malzeme> createMalzeme(@Valid @RequestBody Malzeme malzeme) throws URISyntaxException {
        log.debug("REST request to save Malzeme : {}", malzeme);
        if (malzeme.getId() != null) {
            throw new BadRequestAlertException("A new malzeme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Malzeme result = malzemeService.save(malzeme);
        return ResponseEntity.created(new URI("/api/malzemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /malzemes : Updates an existing malzeme.
     *
     * @param malzeme the malzeme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated malzeme,
     * or with status 400 (Bad Request) if the malzeme is not valid,
     * or with status 500 (Internal Server Error) if the malzeme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/malzemes")
    public ResponseEntity<Malzeme> updateMalzeme(@Valid @RequestBody Malzeme malzeme) throws URISyntaxException {
        log.debug("REST request to update Malzeme : {}", malzeme);
        if (malzeme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Malzeme result = malzemeService.save(malzeme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, malzeme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /malzemes : get all the malzemes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of malzemes in body
     */
    @GetMapping("/malzemes")
    public ResponseEntity<List<Malzeme>> getAllMalzemes(MalzemeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Malzemes by criteria: {}", criteria);
        Page<Malzeme> page = malzemeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/malzemes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /malzemes/count : count all the malzemes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/malzemes/count")
    public ResponseEntity<Long> countMalzemes(MalzemeCriteria criteria) {
        log.debug("REST request to count Malzemes by criteria: {}", criteria);
        return ResponseEntity.ok().body(malzemeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /malzemes/:id : get the "id" malzeme.
     *
     * @param id the id of the malzeme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the malzeme, or with status 404 (Not Found)
     */
    @GetMapping("/malzemes/{id}")
    public ResponseEntity<Malzeme> getMalzeme(@PathVariable Long id) {
        log.debug("REST request to get Malzeme : {}", id);
        Optional<Malzeme> malzeme = malzemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(malzeme);
    }

    /**
     * DELETE  /malzemes/:id : delete the "id" malzeme.
     *
     * @param id the id of the malzeme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/malzemes/{id}")
    public ResponseEntity<Void> deleteMalzeme(@PathVariable Long id) {
        log.debug("REST request to delete Malzeme : {}", id);
        malzemeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

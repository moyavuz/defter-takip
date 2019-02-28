package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.ZimmetTuru;
import com.yavuzturtelekom.service.ZimmetTuruService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.ZimmetTuruCriteria;
import com.yavuzturtelekom.service.ZimmetTuruQueryService;
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
 * REST controller for managing ZimmetTuru.
 */
@RestController
@RequestMapping("/api")
public class ZimmetTuruResource {

    private final Logger log = LoggerFactory.getLogger(ZimmetTuruResource.class);

    private static final String ENTITY_NAME = "zimmetTuru";

    private final ZimmetTuruService zimmetTuruService;

    private final ZimmetTuruQueryService zimmetTuruQueryService;

    public ZimmetTuruResource(ZimmetTuruService zimmetTuruService, ZimmetTuruQueryService zimmetTuruQueryService) {
        this.zimmetTuruService = zimmetTuruService;
        this.zimmetTuruQueryService = zimmetTuruQueryService;
    }

    /**
     * POST  /zimmet-turus : Create a new zimmetTuru.
     *
     * @param zimmetTuru the zimmetTuru to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zimmetTuru, or with status 400 (Bad Request) if the zimmetTuru has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zimmet-turus")
    public ResponseEntity<ZimmetTuru> createZimmetTuru(@Valid @RequestBody ZimmetTuru zimmetTuru) throws URISyntaxException {
        log.debug("REST request to save ZimmetTuru : {}", zimmetTuru);
        if (zimmetTuru.getId() != null) {
            throw new BadRequestAlertException("A new zimmetTuru cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZimmetTuru result = zimmetTuruService.save(zimmetTuru);
        return ResponseEntity.created(new URI("/api/zimmet-turus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zimmet-turus : Updates an existing zimmetTuru.
     *
     * @param zimmetTuru the zimmetTuru to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zimmetTuru,
     * or with status 400 (Bad Request) if the zimmetTuru is not valid,
     * or with status 500 (Internal Server Error) if the zimmetTuru couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zimmet-turus")
    public ResponseEntity<ZimmetTuru> updateZimmetTuru(@Valid @RequestBody ZimmetTuru zimmetTuru) throws URISyntaxException {
        log.debug("REST request to update ZimmetTuru : {}", zimmetTuru);
        if (zimmetTuru.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZimmetTuru result = zimmetTuruService.save(zimmetTuru);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zimmetTuru.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zimmet-turus : get all the zimmetTurus.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of zimmetTurus in body
     */
    @GetMapping("/zimmet-turus")
    public ResponseEntity<List<ZimmetTuru>> getAllZimmetTurus(ZimmetTuruCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ZimmetTurus by criteria: {}", criteria);
        Page<ZimmetTuru> page = zimmetTuruQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zimmet-turus");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /zimmet-turus/count : count all the zimmetTurus.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/zimmet-turus/count")
    public ResponseEntity<Long> countZimmetTurus(ZimmetTuruCriteria criteria) {
        log.debug("REST request to count ZimmetTurus by criteria: {}", criteria);
        return ResponseEntity.ok().body(zimmetTuruQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /zimmet-turus/:id : get the "id" zimmetTuru.
     *
     * @param id the id of the zimmetTuru to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zimmetTuru, or with status 404 (Not Found)
     */
    @GetMapping("/zimmet-turus/{id}")
    public ResponseEntity<ZimmetTuru> getZimmetTuru(@PathVariable Long id) {
        log.debug("REST request to get ZimmetTuru : {}", id);
        Optional<ZimmetTuru> zimmetTuru = zimmetTuruService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zimmetTuru);
    }

    /**
     * DELETE  /zimmet-turus/:id : delete the "id" zimmetTuru.
     *
     * @param id the id of the zimmetTuru to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zimmet-turus/{id}")
    public ResponseEntity<Void> deleteZimmetTuru(@PathVariable Long id) {
        log.debug("REST request to delete ZimmetTuru : {}", id);
        zimmetTuruService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

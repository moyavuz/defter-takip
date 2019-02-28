package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.service.StokTakipService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.StokTakipCriteria;
import com.yavuzturtelekom.service.StokTakipQueryService;
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
 * REST controller for managing StokTakip.
 */
@RestController
@RequestMapping("/api")
public class StokTakipResource {

    private final Logger log = LoggerFactory.getLogger(StokTakipResource.class);

    private static final String ENTITY_NAME = "stokTakip";

    private final StokTakipService stokTakipService;

    private final StokTakipQueryService stokTakipQueryService;

    public StokTakipResource(StokTakipService stokTakipService, StokTakipQueryService stokTakipQueryService) {
        this.stokTakipService = stokTakipService;
        this.stokTakipQueryService = stokTakipQueryService;
    }

    /**
     * POST  /stok-takips : Create a new stokTakip.
     *
     * @param stokTakip the stokTakip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stokTakip, or with status 400 (Bad Request) if the stokTakip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stok-takips")
    public ResponseEntity<StokTakip> createStokTakip(@Valid @RequestBody StokTakip stokTakip) throws URISyntaxException {
        log.debug("REST request to save StokTakip : {}", stokTakip);
        if (stokTakip.getId() != null) {
            throw new BadRequestAlertException("A new stokTakip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StokTakip result = stokTakipService.save(stokTakip);
        return ResponseEntity.created(new URI("/api/stok-takips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stok-takips : Updates an existing stokTakip.
     *
     * @param stokTakip the stokTakip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stokTakip,
     * or with status 400 (Bad Request) if the stokTakip is not valid,
     * or with status 500 (Internal Server Error) if the stokTakip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stok-takips")
    public ResponseEntity<StokTakip> updateStokTakip(@Valid @RequestBody StokTakip stokTakip) throws URISyntaxException {
        log.debug("REST request to update StokTakip : {}", stokTakip);
        if (stokTakip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StokTakip result = stokTakipService.save(stokTakip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stokTakip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stok-takips : get all the stokTakips.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stokTakips in body
     */
    @GetMapping("/stok-takips")
    public ResponseEntity<List<StokTakip>> getAllStokTakips(StokTakipCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StokTakips by criteria: {}", criteria);
        Page<StokTakip> page = stokTakipQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stok-takips");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /stok-takips/count : count all the stokTakips.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/stok-takips/count")
    public ResponseEntity<Long> countStokTakips(StokTakipCriteria criteria) {
        log.debug("REST request to count StokTakips by criteria: {}", criteria);
        return ResponseEntity.ok().body(stokTakipQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /stok-takips/:id : get the "id" stokTakip.
     *
     * @param id the id of the stokTakip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stokTakip, or with status 404 (Not Found)
     */
    @GetMapping("/stok-takips/{id}")
    public ResponseEntity<StokTakip> getStokTakip(@PathVariable Long id) {
        log.debug("REST request to get StokTakip : {}", id);
        Optional<StokTakip> stokTakip = stokTakipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stokTakip);
    }

    /**
     * DELETE  /stok-takips/:id : delete the "id" stokTakip.
     *
     * @param id the id of the stokTakip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stok-takips/{id}")
    public ResponseEntity<Void> deleteStokTakip(@PathVariable Long id) {
        log.debug("REST request to delete StokTakip : {}", id);
        stokTakipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

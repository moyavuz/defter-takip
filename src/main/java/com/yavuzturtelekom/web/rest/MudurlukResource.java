package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.service.MudurlukService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import com.yavuzturtelekom.web.rest.util.PaginationUtil;
import com.yavuzturtelekom.service.dto.MudurlukCriteria;
import com.yavuzturtelekom.service.MudurlukQueryService;
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
 * REST controller for managing Mudurluk.
 */
@RestController
@RequestMapping("/api")
public class MudurlukResource {

    private final Logger log = LoggerFactory.getLogger(MudurlukResource.class);

    private static final String ENTITY_NAME = "mudurluk";

    private final MudurlukService mudurlukService;

    private final MudurlukQueryService mudurlukQueryService;

    public MudurlukResource(MudurlukService mudurlukService, MudurlukQueryService mudurlukQueryService) {
        this.mudurlukService = mudurlukService;
        this.mudurlukQueryService = mudurlukQueryService;
    }

    /**
     * POST  /mudurluks : Create a new mudurluk.
     *
     * @param mudurluk the mudurluk to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mudurluk, or with status 400 (Bad Request) if the mudurluk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mudurluks")
    public ResponseEntity<Mudurluk> createMudurluk(@Valid @RequestBody Mudurluk mudurluk) throws URISyntaxException {
        log.debug("REST request to save Mudurluk : {}", mudurluk);
        if (mudurluk.getId() != null) {
            throw new BadRequestAlertException("A new mudurluk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mudurluk result = mudurlukService.save(mudurluk);
        return ResponseEntity.created(new URI("/api/mudurluks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mudurluks : Updates an existing mudurluk.
     *
     * @param mudurluk the mudurluk to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mudurluk,
     * or with status 400 (Bad Request) if the mudurluk is not valid,
     * or with status 500 (Internal Server Error) if the mudurluk couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mudurluks")
    public ResponseEntity<Mudurluk> updateMudurluk(@Valid @RequestBody Mudurluk mudurluk) throws URISyntaxException {
        log.debug("REST request to update Mudurluk : {}", mudurluk);
        if (mudurluk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mudurluk result = mudurlukService.save(mudurluk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mudurluk.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mudurluks : get all the mudurluks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of mudurluks in body
     */
    @GetMapping("/mudurluks")
    public ResponseEntity<List<Mudurluk>> getAllMudurluks(MudurlukCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Mudurluks by criteria: {}", criteria);
        Page<Mudurluk> page = mudurlukQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mudurluks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /mudurluks/count : count all the mudurluks.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/mudurluks/count")
    public ResponseEntity<Long> countMudurluks(MudurlukCriteria criteria) {
        log.debug("REST request to count Mudurluks by criteria: {}", criteria);
        return ResponseEntity.ok().body(mudurlukQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /mudurluks/:id : get the "id" mudurluk.
     *
     * @param id the id of the mudurluk to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mudurluk, or with status 404 (Not Found)
     */
    @GetMapping("/mudurluks/{id}")
    public ResponseEntity<Mudurluk> getMudurluk(@PathVariable Long id) {
        log.debug("REST request to get Mudurluk : {}", id);
        Optional<Mudurluk> mudurluk = mudurlukService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mudurluk);
    }

    /**
     * DELETE  /mudurluks/:id : delete the "id" mudurluk.
     *
     * @param id the id of the mudurluk to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mudurluks/{id}")
    public ResponseEntity<Void> deleteMudurluk(@PathVariable Long id) {
        log.debug("REST request to delete Mudurluk : {}", id);
        mudurlukService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

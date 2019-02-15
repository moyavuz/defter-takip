package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.MalzemeTakip;
import com.yavuzturtelekom.service.MalzemeTakipService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MalzemeTakip.
 */
@RestController
@RequestMapping("/api")
public class MalzemeTakipResource {

    private final Logger log = LoggerFactory.getLogger(MalzemeTakipResource.class);

    private static final String ENTITY_NAME = "malzemeTakip";

    private final MalzemeTakipService malzemeTakipService;

    public MalzemeTakipResource(MalzemeTakipService malzemeTakipService) {
        this.malzemeTakipService = malzemeTakipService;
    }

    /**
     * POST  /malzeme-takips : Create a new malzemeTakip.
     *
     * @param malzemeTakip the malzemeTakip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new malzemeTakip, or with status 400 (Bad Request) if the malzemeTakip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/malzeme-takips")
    public ResponseEntity<MalzemeTakip> createMalzemeTakip(@Valid @RequestBody MalzemeTakip malzemeTakip) throws URISyntaxException {
        log.debug("REST request to save MalzemeTakip : {}", malzemeTakip);
        if (malzemeTakip.getId() != null) {
            throw new BadRequestAlertException("A new malzemeTakip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MalzemeTakip result = malzemeTakipService.save(malzemeTakip);
        return ResponseEntity.created(new URI("/api/malzeme-takips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /malzeme-takips : Updates an existing malzemeTakip.
     *
     * @param malzemeTakip the malzemeTakip to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated malzemeTakip,
     * or with status 400 (Bad Request) if the malzemeTakip is not valid,
     * or with status 500 (Internal Server Error) if the malzemeTakip couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/malzeme-takips")
    public ResponseEntity<MalzemeTakip> updateMalzemeTakip(@Valid @RequestBody MalzemeTakip malzemeTakip) throws URISyntaxException {
        log.debug("REST request to update MalzemeTakip : {}", malzemeTakip);
        if (malzemeTakip.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MalzemeTakip result = malzemeTakipService.save(malzemeTakip);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, malzemeTakip.getId().toString()))
            .body(result);
    }

    /**
     * GET  /malzeme-takips : get all the malzemeTakips.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of malzemeTakips in body
     */
    @GetMapping("/malzeme-takips")
    public ResponseEntity<List<MalzemeTakip>> getAllMalzemeTakips(Pageable pageable) {
        log.debug("REST request to get a page of MalzemeTakips");
        Page<MalzemeTakip> page = malzemeTakipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/malzeme-takips");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /malzeme-takips/:id : get the "id" malzemeTakip.
     *
     * @param id the id of the malzemeTakip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the malzemeTakip, or with status 404 (Not Found)
     */
    @GetMapping("/malzeme-takips/{id}")
    public ResponseEntity<MalzemeTakip> getMalzemeTakip(@PathVariable Long id) {
        log.debug("REST request to get MalzemeTakip : {}", id);
        Optional<MalzemeTakip> malzemeTakip = malzemeTakipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(malzemeTakip);
    }

    /**
     * DELETE  /malzeme-takips/:id : delete the "id" malzemeTakip.
     *
     * @param id the id of the malzemeTakip to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/malzeme-takips/{id}")
    public ResponseEntity<Void> deleteMalzemeTakip(@PathVariable Long id) {
        log.debug("REST request to delete MalzemeTakip : {}", id);
        malzemeTakipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

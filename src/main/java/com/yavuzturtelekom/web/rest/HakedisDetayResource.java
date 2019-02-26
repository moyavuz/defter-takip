package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.HakedisDetay;
import com.yavuzturtelekom.service.HakedisDetayService;
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
 * REST controller for managing HakedisDetay.
 */
@RestController
@RequestMapping("/api")
public class HakedisDetayResource {

    private final Logger log = LoggerFactory.getLogger(HakedisDetayResource.class);

    private static final String ENTITY_NAME = "hakedisDetay";

    private final HakedisDetayService hakedisDetayService;

    public HakedisDetayResource(HakedisDetayService hakedisDetayService) {
        this.hakedisDetayService = hakedisDetayService;
    }

    /**
     * POST  /hakedis-detays : Create a new hakedisDetay.
     *
     * @param hakedisDetay the hakedisDetay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hakedisDetay, or with status 400 (Bad Request) if the hakedisDetay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hakedis-detays")
    public ResponseEntity<HakedisDetay> createHakedisDetay(@Valid @RequestBody HakedisDetay hakedisDetay) throws URISyntaxException {
        log.debug("REST request to save HakedisDetay : {}", hakedisDetay);
        if (hakedisDetay.getId() != null) {
            throw new BadRequestAlertException("A new hakedisDetay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HakedisDetay result = hakedisDetayService.save(hakedisDetay);
        return ResponseEntity.created(new URI("/api/hakedis-detays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hakedis-detays : Updates an existing hakedisDetay.
     *
     * @param hakedisDetay the hakedisDetay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hakedisDetay,
     * or with status 400 (Bad Request) if the hakedisDetay is not valid,
     * or with status 500 (Internal Server Error) if the hakedisDetay couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hakedis-detays")
    public ResponseEntity<HakedisDetay> updateHakedisDetay(@Valid @RequestBody HakedisDetay hakedisDetay) throws URISyntaxException {
        log.debug("REST request to update HakedisDetay : {}", hakedisDetay);
        if (hakedisDetay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HakedisDetay result = hakedisDetayService.save(hakedisDetay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hakedisDetay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hakedis-detays : get all the hakedisDetays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of hakedisDetays in body
     */
    @GetMapping("/hakedis-detays")
    public ResponseEntity<List<HakedisDetay>> getAllHakedisDetays(Pageable pageable) {
        log.debug("REST request to get a page of HakedisDetays");
        Page<HakedisDetay> page = hakedisDetayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hakedis-detays");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /hakedis-detays/:id : get the "id" hakedisDetay.
     *
     * @param id the id of the hakedisDetay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hakedisDetay, or with status 404 (Not Found)
     */
    @GetMapping("/hakedis-detays/{id}")
    public ResponseEntity<HakedisDetay> getHakedisDetay(@PathVariable Long id) {
        log.debug("REST request to get HakedisDetay : {}", id);
        Optional<HakedisDetay> hakedisDetay = hakedisDetayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hakedisDetay);
    }

    /**
     * DELETE  /hakedis-detays/:id : delete the "id" hakedisDetay.
     *
     * @param id the id of the hakedisDetay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hakedis-detays/{id}")
    public ResponseEntity<Void> deleteHakedisDetay(@PathVariable Long id) {
        log.debug("REST request to delete HakedisDetay : {}", id);
        hakedisDetayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/hakedis-detays/hakedis/{hakedisId}")
    public List<HakedisDetay> getHakedisDetayByHakedis(@PathVariable Long hakedisId) {
        log.debug("REST request to get HakedisDetay by Hakedis Id: {}", hakedisId);

        Optional<HakedisDetay> hakedisDetay = hakedisDetayService.findOne(hakedisId);

        List<HakedisDetay> actions = hakedisDetayService.findHakedisDetayByHakedisId(hakedisId);

        return actions;
    }

}

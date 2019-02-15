package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.IscilikDetay;
import com.yavuzturtelekom.service.IscilikDetayService;
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
 * REST controller for managing IscilikDetay.
 */
@RestController
@RequestMapping("/api")
public class IscilikDetayResource {

    private final Logger log = LoggerFactory.getLogger(IscilikDetayResource.class);

    private static final String ENTITY_NAME = "iscilikDetay";

    private final IscilikDetayService iscilikDetayService;

    public IscilikDetayResource(IscilikDetayService iscilikDetayService) {
        this.iscilikDetayService = iscilikDetayService;
    }

    /**
     * POST  /iscilik-detays : Create a new iscilikDetay.
     *
     * @param iscilikDetay the iscilikDetay to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iscilikDetay, or with status 400 (Bad Request) if the iscilikDetay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/iscilik-detays")
    public ResponseEntity<IscilikDetay> createIscilikDetay(@Valid @RequestBody IscilikDetay iscilikDetay) throws URISyntaxException {
        log.debug("REST request to save IscilikDetay : {}", iscilikDetay);
        if (iscilikDetay.getId() != null) {
            throw new BadRequestAlertException("A new iscilikDetay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IscilikDetay result = iscilikDetayService.save(iscilikDetay);
        return ResponseEntity.created(new URI("/api/iscilik-detays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /iscilik-detays : Updates an existing iscilikDetay.
     *
     * @param iscilikDetay the iscilikDetay to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iscilikDetay,
     * or with status 400 (Bad Request) if the iscilikDetay is not valid,
     * or with status 500 (Internal Server Error) if the iscilikDetay couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/iscilik-detays")
    public ResponseEntity<IscilikDetay> updateIscilikDetay(@Valid @RequestBody IscilikDetay iscilikDetay) throws URISyntaxException {
        log.debug("REST request to update IscilikDetay : {}", iscilikDetay);
        if (iscilikDetay.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IscilikDetay result = iscilikDetayService.save(iscilikDetay);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iscilikDetay.getId().toString()))
            .body(result);
    }

    /**
     * GET  /iscilik-detays : get all the iscilikDetays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of iscilikDetays in body
     */
    @GetMapping("/iscilik-detays")
    public ResponseEntity<List<IscilikDetay>> getAllIscilikDetays(Pageable pageable) {
        log.debug("REST request to get a page of IscilikDetays");
        Page<IscilikDetay> page = iscilikDetayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/iscilik-detays");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /iscilik-detays/:id : get the "id" iscilikDetay.
     *
     * @param id the id of the iscilikDetay to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iscilikDetay, or with status 404 (Not Found)
     */
    @GetMapping("/iscilik-detays/{id}")
    public ResponseEntity<IscilikDetay> getIscilikDetay(@PathVariable Long id) {
        log.debug("REST request to get IscilikDetay : {}", id);
        Optional<IscilikDetay> iscilikDetay = iscilikDetayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iscilikDetay);
    }

    /**
     * DELETE  /iscilik-detays/:id : delete the "id" iscilikDetay.
     *
     * @param id the id of the iscilikDetay to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/iscilik-detays/{id}")
    public ResponseEntity<Void> deleteIscilikDetay(@PathVariable Long id) {
        log.debug("REST request to delete IscilikDetay : {}", id);
        iscilikDetayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

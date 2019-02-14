package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Iscilik;
import com.yavuzturtelekom.service.IscilikService;
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
 * REST controller for managing Iscilik.
 */
@RestController
@RequestMapping("/api")
public class IscilikResource {

    private final Logger log = LoggerFactory.getLogger(IscilikResource.class);

    private static final String ENTITY_NAME = "iscilik";

    private final IscilikService iscilikService;

    public IscilikResource(IscilikService iscilikService) {
        this.iscilikService = iscilikService;
    }

    /**
     * POST  /isciliks : Create a new iscilik.
     *
     * @param iscilik the iscilik to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iscilik, or with status 400 (Bad Request) if the iscilik has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/isciliks")
    public ResponseEntity<Iscilik> createIscilik(@Valid @RequestBody Iscilik iscilik) throws URISyntaxException {
        log.debug("REST request to save Iscilik : {}", iscilik);
        if (iscilik.getId() != null) {
            throw new BadRequestAlertException("A new iscilik cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Iscilik result = iscilikService.save(iscilik);
        return ResponseEntity.created(new URI("/api/isciliks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /isciliks : Updates an existing iscilik.
     *
     * @param iscilik the iscilik to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iscilik,
     * or with status 400 (Bad Request) if the iscilik is not valid,
     * or with status 500 (Internal Server Error) if the iscilik couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/isciliks")
    public ResponseEntity<Iscilik> updateIscilik(@Valid @RequestBody Iscilik iscilik) throws URISyntaxException {
        log.debug("REST request to update Iscilik : {}", iscilik);
        if (iscilik.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Iscilik result = iscilikService.save(iscilik);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iscilik.getId().toString()))
            .body(result);
    }

    /**
     * GET  /isciliks : get all the isciliks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of isciliks in body
     */
    @GetMapping("/isciliks")
    public ResponseEntity<List<Iscilik>> getAllIsciliks(Pageable pageable) {
        log.debug("REST request to get a page of Isciliks");
        Page<Iscilik> page = iscilikService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/isciliks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /isciliks/:id : get the "id" iscilik.
     *
     * @param id the id of the iscilik to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iscilik, or with status 404 (Not Found)
     */
    @GetMapping("/isciliks/{id}")
    public ResponseEntity<Iscilik> getIscilik(@PathVariable Long id) {
        log.debug("REST request to get Iscilik : {}", id);
        Optional<Iscilik> iscilik = iscilikService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iscilik);
    }

    /**
     * DELETE  /isciliks/:id : delete the "id" iscilik.
     *
     * @param id the id of the iscilik to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/isciliks/{id}")
    public ResponseEntity<Void> deleteIscilik(@PathVariable Long id) {
        log.debug("REST request to delete Iscilik : {}", id);
        iscilikService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

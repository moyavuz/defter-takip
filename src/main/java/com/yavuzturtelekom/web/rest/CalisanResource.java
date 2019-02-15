package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Calisan;
import com.yavuzturtelekom.service.CalisanService;
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
 * REST controller for managing Calisan.
 */
@RestController
@RequestMapping("/api")
public class CalisanResource {

    private final Logger log = LoggerFactory.getLogger(CalisanResource.class);

    private static final String ENTITY_NAME = "calisan";

    private final CalisanService calisanService;

    public CalisanResource(CalisanService calisanService) {
        this.calisanService = calisanService;
    }

    /**
     * POST  /calisans : Create a new calisan.
     *
     * @param calisan the calisan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calisan, or with status 400 (Bad Request) if the calisan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calisans")
    public ResponseEntity<Calisan> createCalisan(@Valid @RequestBody Calisan calisan) throws URISyntaxException {
        log.debug("REST request to save Calisan : {}", calisan);
        if (calisan.getId() != null) {
            throw new BadRequestAlertException("A new calisan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calisan result = calisanService.save(calisan);
        return ResponseEntity.created(new URI("/api/calisans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calisans : Updates an existing calisan.
     *
     * @param calisan the calisan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calisan,
     * or with status 400 (Bad Request) if the calisan is not valid,
     * or with status 500 (Internal Server Error) if the calisan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calisans")
    public ResponseEntity<Calisan> updateCalisan(@Valid @RequestBody Calisan calisan) throws URISyntaxException {
        log.debug("REST request to update Calisan : {}", calisan);
        if (calisan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Calisan result = calisanService.save(calisan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, calisan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calisans : get all the calisans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of calisans in body
     */
    @GetMapping("/calisans")
    public ResponseEntity<List<Calisan>> getAllCalisans(Pageable pageable) {
        log.debug("REST request to get a page of Calisans");
        Page<Calisan> page = calisanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/calisans");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /calisans/:id : get the "id" calisan.
     *
     * @param id the id of the calisan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calisan, or with status 404 (Not Found)
     */
    @GetMapping("/calisans/{id}")
    public ResponseEntity<Calisan> getCalisan(@PathVariable Long id) {
        log.debug("REST request to get Calisan : {}", id);
        Optional<Calisan> calisan = calisanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calisan);
    }

    /**
     * DELETE  /calisans/:id : delete the "id" calisan.
     *
     * @param id the id of the calisan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calisans/{id}")
    public ResponseEntity<Void> deleteCalisan(@PathVariable Long id) {
        log.debug("REST request to delete Calisan : {}", id);
        calisanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

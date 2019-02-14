package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.service.MalzemeService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Malzeme.
 */
@RestController
@RequestMapping("/api")
public class MalzemeResource {

    private final Logger log = LoggerFactory.getLogger(MalzemeResource.class);

    private static final String ENTITY_NAME = "malzeme";

    private final MalzemeService malzemeService;

    public MalzemeResource(MalzemeService malzemeService) {
        this.malzemeService = malzemeService;
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
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of malzemes in body
     */
    @GetMapping("/malzemes")
    public ResponseEntity<List<Malzeme>> getAllMalzemes(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("poz-is-null".equals(filter)) {
            log.debug("REST request to get all Malzemes where poz is null");
            return new ResponseEntity<>(malzemeService.findAllWherePozIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Malzemes");
        Page<Malzeme> page = malzemeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/malzemes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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

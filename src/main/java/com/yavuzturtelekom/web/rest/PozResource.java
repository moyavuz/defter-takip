package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.repository.PozRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Poz.
 */
@RestController
@RequestMapping("/api")
public class PozResource {

    private final Logger log = LoggerFactory.getLogger(PozResource.class);

    private static final String ENTITY_NAME = "poz";

    private final PozRepository pozRepository;

    public PozResource(PozRepository pozRepository) {
        this.pozRepository = pozRepository;
    }

    /**
     * POST  /pozs : Create a new poz.
     *
     * @param poz the poz to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poz, or with status 400 (Bad Request) if the poz has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pozs")
    public ResponseEntity<Poz> createPoz(@Valid @RequestBody Poz poz) throws URISyntaxException {
        log.debug("REST request to save Poz : {}", poz);
        if (poz.getId() != null) {
            throw new BadRequestAlertException("A new poz cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Poz result = pozRepository.save(poz);
        return ResponseEntity.created(new URI("/api/pozs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pozs : Updates an existing poz.
     *
     * @param poz the poz to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poz,
     * or with status 400 (Bad Request) if the poz is not valid,
     * or with status 500 (Internal Server Error) if the poz couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pozs")
    public ResponseEntity<Poz> updatePoz(@Valid @RequestBody Poz poz) throws URISyntaxException {
        log.debug("REST request to update Poz : {}", poz);
        if (poz.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Poz result = pozRepository.save(poz);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poz.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pozs : get all the pozs.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of pozs in body
     */
    @GetMapping("/pozs")
    public ResponseEntity<List<Poz>> getAllPozs(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("malzeme-is-null".equals(filter)) {
            log.debug("REST request to get all Pozs where malzeme is null");
            return new ResponseEntity<>(StreamSupport
                .stream(pozRepository.findAll().spliterator(), false)
                .filter(poz -> poz.getMalzeme() == null)
                .collect(Collectors.toList()), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Pozs");
        Page<Poz> page = pozRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pozs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pozs/:id : get the "id" poz.
     *
     * @param id the id of the poz to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poz, or with status 404 (Not Found)
     */
    @GetMapping("/pozs/{id}")
    public ResponseEntity<Poz> getPoz(@PathVariable Long id) {
        log.debug("REST request to get Poz : {}", id);
        Optional<Poz> poz = pozRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(poz);
    }

    /**
     * DELETE  /pozs/:id : delete the "id" poz.
     *
     * @param id the id of the poz to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pozs/{id}")
    public ResponseEntity<Void> deletePoz(@PathVariable Long id) {
        log.debug("REST request to delete Poz : {}", id);
        pozRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

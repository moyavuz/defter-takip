package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.DefterTuru;
import com.yavuzturtelekom.service.DefterTuruService;
import com.yavuzturtelekom.web.rest.errors.BadRequestAlertException;
import com.yavuzturtelekom.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DefterTuru.
 */
@RestController
@RequestMapping("/api")
public class DefterTuruResource {

    private final Logger log = LoggerFactory.getLogger(DefterTuruResource.class);

    private static final String ENTITY_NAME = "defterTuru";

    private final DefterTuruService defterTuruService;

    public DefterTuruResource(DefterTuruService defterTuruService) {
        this.defterTuruService = defterTuruService;
    }

    /**
     * POST  /defter-turus : Create a new defterTuru.
     *
     * @param defterTuru the defterTuru to create
     * @return the ResponseEntity with status 201 (Created) and with body the new defterTuru, or with status 400 (Bad Request) if the defterTuru has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/defter-turus")
    public ResponseEntity<DefterTuru> createDefterTuru(@Valid @RequestBody DefterTuru defterTuru) throws URISyntaxException {
        log.debug("REST request to save DefterTuru : {}", defterTuru);
        if (defterTuru.getId() != null) {
            throw new BadRequestAlertException("A new defterTuru cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefterTuru result = defterTuruService.save(defterTuru);
        return ResponseEntity.created(new URI("/api/defter-turus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /defter-turus : Updates an existing defterTuru.
     *
     * @param defterTuru the defterTuru to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated defterTuru,
     * or with status 400 (Bad Request) if the defterTuru is not valid,
     * or with status 500 (Internal Server Error) if the defterTuru couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/defter-turus")
    public ResponseEntity<DefterTuru> updateDefterTuru(@Valid @RequestBody DefterTuru defterTuru) throws URISyntaxException {
        log.debug("REST request to update DefterTuru : {}", defterTuru);
        if (defterTuru.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DefterTuru result = defterTuruService.save(defterTuru);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, defterTuru.getId().toString()))
            .body(result);
    }

    /**
     * GET  /defter-turus : get all the defterTurus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of defterTurus in body
     */
    @GetMapping("/defter-turus")
    public List<DefterTuru> getAllDefterTurus() {
        log.debug("REST request to get all DefterTurus");
        return defterTuruService.findAll();
    }

    /**
     * GET  /defter-turus/:id : get the "id" defterTuru.
     *
     * @param id the id of the defterTuru to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the defterTuru, or with status 404 (Not Found)
     */
    @GetMapping("/defter-turus/{id}")
    public ResponseEntity<DefterTuru> getDefterTuru(@PathVariable Long id) {
        log.debug("REST request to get DefterTuru : {}", id);
        Optional<DefterTuru> defterTuru = defterTuruService.findOne(id);
        return ResponseUtil.wrapOrNotFound(defterTuru);
    }

    /**
     * DELETE  /defter-turus/:id : delete the "id" defterTuru.
     *
     * @param id the id of the defterTuru to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/defter-turus/{id}")
    public ResponseEntity<Void> deleteDefterTuru(@PathVariable Long id) {
        log.debug("REST request to delete DefterTuru : {}", id);
        defterTuruService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

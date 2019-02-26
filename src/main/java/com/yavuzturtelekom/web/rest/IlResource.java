package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Il;
import com.yavuzturtelekom.service.IlService;
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
 * REST controller for managing Il.
 */
@RestController
@RequestMapping("/api")
public class IlResource {

    private final Logger log = LoggerFactory.getLogger(IlResource.class);

    private static final String ENTITY_NAME = "il";

    private final IlService ilService;

    public IlResource(IlService ilService) {
        this.ilService = ilService;
    }

    /**
     * POST  /ils : Create a new il.
     *
     * @param il the il to create
     * @return the ResponseEntity with status 201 (Created) and with body the new il, or with status 400 (Bad Request) if the il has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ils")
    public ResponseEntity<Il> createIl(@Valid @RequestBody Il il) throws URISyntaxException {
        log.debug("REST request to save Il : {}", il);
        if (il.getId() != null) {
            throw new BadRequestAlertException("A new il cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Il result = ilService.save(il);
        return ResponseEntity.created(new URI("/api/ils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ils : Updates an existing il.
     *
     * @param il the il to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated il,
     * or with status 400 (Bad Request) if the il is not valid,
     * or with status 500 (Internal Server Error) if the il couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ils")
    public ResponseEntity<Il> updateIl(@Valid @RequestBody Il il) throws URISyntaxException {
        log.debug("REST request to update Il : {}", il);
        if (il.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Il result = ilService.save(il);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, il.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ils : get all the ils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ils in body
     */
    @GetMapping("/ils")
    public List<Il> getAllIls() {
        log.debug("REST request to get all Ils");
        return ilService.findAll();
    }

    /**
     * GET  /ils/:id : get the "id" il.
     *
     * @param id the id of the il to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the il, or with status 404 (Not Found)
     */
    @GetMapping("/ils/{id}")
    public ResponseEntity<Il> getIl(@PathVariable Long id) {
        log.debug("REST request to get Il : {}", id);
        Optional<Il> il = ilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(il);
    }

    /**
     * DELETE  /ils/:id : delete the "id" il.
     *
     * @param id the id of the il to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ils/{id}")
    public ResponseEntity<Void> deleteIl(@PathVariable Long id) {
        log.debug("REST request to delete Il : {}", id);
        ilService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

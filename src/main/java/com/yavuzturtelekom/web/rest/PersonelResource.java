package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.service.PersonelService;
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
 * REST controller for managing Personel.
 */
@RestController
@RequestMapping("/api")
public class PersonelResource {

    private final Logger log = LoggerFactory.getLogger(PersonelResource.class);

    private static final String ENTITY_NAME = "personel";

    private final PersonelService personelService;

    public PersonelResource(PersonelService personelService) {
        this.personelService = personelService;
    }

    /**
     * POST  /personels : Create a new personel.
     *
     * @param personel the personel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personel, or with status 400 (Bad Request) if the personel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personels")
    public ResponseEntity<Personel> createPersonel(@Valid @RequestBody Personel personel) throws URISyntaxException {
        log.debug("REST request to save Personel : {}", personel);
        if (personel.getId() != null) {
            throw new BadRequestAlertException("A new personel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Personel result = personelService.save(personel);
        return ResponseEntity.created(new URI("/api/personels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personels : Updates an existing personel.
     *
     * @param personel the personel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personel,
     * or with status 400 (Bad Request) if the personel is not valid,
     * or with status 500 (Internal Server Error) if the personel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personels")
    public ResponseEntity<Personel> updatePersonel(@Valid @RequestBody Personel personel) throws URISyntaxException {
        log.debug("REST request to update Personel : {}", personel);
        if (personel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Personel result = personelService.save(personel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personels : get all the personels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personels in body
     */
    @GetMapping("/personels")
    public ResponseEntity<List<Personel>> getAllPersonels(Pageable pageable) {
        log.debug("REST request to get a page of Personels");
        Page<Personel> page = personelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/personels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /personels/:id : get the "id" personel.
     *
     * @param id the id of the personel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personel, or with status 404 (Not Found)
     */
    @GetMapping("/personels/{id}")
    public ResponseEntity<Personel> getPersonel(@PathVariable Long id) {
        log.debug("REST request to get Personel : {}", id);
        Optional<Personel> personel = personelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personel);
    }

    /**
     * DELETE  /personels/:id : delete the "id" personel.
     *
     * @param id the id of the personel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personels/{id}")
    public ResponseEntity<Void> deletePersonel(@PathVariable Long id) {
        log.debug("REST request to delete Personel : {}", id);
        personelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

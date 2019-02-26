package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.PersonelZimmet;
import com.yavuzturtelekom.service.PersonelZimmetService;
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
 * REST controller for managing PersonelZimmet.
 */
@RestController
@RequestMapping("/api")
public class PersonelZimmetResource {

    private final Logger log = LoggerFactory.getLogger(PersonelZimmetResource.class);

    private static final String ENTITY_NAME = "personelZimmet";

    private final PersonelZimmetService personelZimmetService;

    public PersonelZimmetResource(PersonelZimmetService personelZimmetService) {
        this.personelZimmetService = personelZimmetService;
    }

    /**
     * POST  /personel-zimmets : Create a new personelZimmet.
     *
     * @param personelZimmet the personelZimmet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personelZimmet, or with status 400 (Bad Request) if the personelZimmet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personel-zimmets")
    public ResponseEntity<PersonelZimmet> createPersonelZimmet(@Valid @RequestBody PersonelZimmet personelZimmet) throws URISyntaxException {
        log.debug("REST request to save PersonelZimmet : {}", personelZimmet);
        if (personelZimmet.getId() != null) {
            throw new BadRequestAlertException("A new personelZimmet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonelZimmet result = personelZimmetService.save(personelZimmet);
        return ResponseEntity.created(new URI("/api/personel-zimmets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personel-zimmets : Updates an existing personelZimmet.
     *
     * @param personelZimmet the personelZimmet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personelZimmet,
     * or with status 400 (Bad Request) if the personelZimmet is not valid,
     * or with status 500 (Internal Server Error) if the personelZimmet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personel-zimmets")
    public ResponseEntity<PersonelZimmet> updatePersonelZimmet(@Valid @RequestBody PersonelZimmet personelZimmet) throws URISyntaxException {
        log.debug("REST request to update PersonelZimmet : {}", personelZimmet);
        if (personelZimmet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonelZimmet result = personelZimmetService.save(personelZimmet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personelZimmet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personel-zimmets : get all the personelZimmets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personelZimmets in body
     */
    @GetMapping("/personel-zimmets")
    public List<PersonelZimmet> getAllPersonelZimmets() {
        log.debug("REST request to get all PersonelZimmets");
        return personelZimmetService.findAll();
    }

    /**
     * GET  /personel-zimmets/:id : get the "id" personelZimmet.
     *
     * @param id the id of the personelZimmet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personelZimmet, or with status 404 (Not Found)
     */
    @GetMapping("/personel-zimmets/{id}")
    public ResponseEntity<PersonelZimmet> getPersonelZimmet(@PathVariable Long id) {
        log.debug("REST request to get PersonelZimmet : {}", id);
        Optional<PersonelZimmet> personelZimmet = personelZimmetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personelZimmet);
    }

    /**
     * DELETE  /personel-zimmets/:id : delete the "id" personelZimmet.
     *
     * @param id the id of the personelZimmet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personel-zimmets/{id}")
    public ResponseEntity<Void> deletePersonelZimmet(@PathVariable Long id) {
        log.debug("REST request to delete PersonelZimmet : {}", id);
        personelZimmetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

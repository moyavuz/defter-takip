package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.PersonelArac;
import com.yavuzturtelekom.service.PersonelAracService;
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
 * REST controller for managing PersonelArac.
 */
@RestController
@RequestMapping("/api")
public class PersonelAracResource {

    private final Logger log = LoggerFactory.getLogger(PersonelAracResource.class);

    private static final String ENTITY_NAME = "personelArac";

    private final PersonelAracService personelAracService;

    public PersonelAracResource(PersonelAracService personelAracService) {
        this.personelAracService = personelAracService;
    }

    /**
     * POST  /personel-aracs : Create a new personelArac.
     *
     * @param personelArac the personelArac to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personelArac, or with status 400 (Bad Request) if the personelArac has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personel-aracs")
    public ResponseEntity<PersonelArac> createPersonelArac(@Valid @RequestBody PersonelArac personelArac) throws URISyntaxException {
        log.debug("REST request to save PersonelArac : {}", personelArac);
        if (personelArac.getId() != null) {
            throw new BadRequestAlertException("A new personelArac cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonelArac result = personelAracService.save(personelArac);
        return ResponseEntity.created(new URI("/api/personel-aracs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personel-aracs : Updates an existing personelArac.
     *
     * @param personelArac the personelArac to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personelArac,
     * or with status 400 (Bad Request) if the personelArac is not valid,
     * or with status 500 (Internal Server Error) if the personelArac couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personel-aracs")
    public ResponseEntity<PersonelArac> updatePersonelArac(@Valid @RequestBody PersonelArac personelArac) throws URISyntaxException {
        log.debug("REST request to update PersonelArac : {}", personelArac);
        if (personelArac.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonelArac result = personelAracService.save(personelArac);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personelArac.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personel-aracs : get all the personelAracs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personelAracs in body
     */
    @GetMapping("/personel-aracs")
    public List<PersonelArac> getAllPersonelAracs() {
        log.debug("REST request to get all PersonelAracs");
        return personelAracService.findAll();
    }

    /**
     * GET  /personel-aracs/:id : get the "id" personelArac.
     *
     * @param id the id of the personelArac to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personelArac, or with status 404 (Not Found)
     */
    @GetMapping("/personel-aracs/{id}")
    public ResponseEntity<PersonelArac> getPersonelArac(@PathVariable Long id) {
        log.debug("REST request to get PersonelArac : {}", id);
        Optional<PersonelArac> personelArac = personelAracService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personelArac);
    }

    /**
     * DELETE  /personel-aracs/:id : delete the "id" personelArac.
     *
     * @param id the id of the personelArac to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personel-aracs/{id}")
    public ResponseEntity<Void> deletePersonelArac(@PathVariable Long id) {
        log.debug("REST request to delete PersonelArac : {}", id);
        personelAracService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.PersonelOdeme;
import com.yavuzturtelekom.service.PersonelOdemeService;
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
 * REST controller for managing PersonelOdeme.
 */
@RestController
@RequestMapping("/api")
public class PersonelOdemeResource {

    private final Logger log = LoggerFactory.getLogger(PersonelOdemeResource.class);

    private static final String ENTITY_NAME = "personelOdeme";

    private final PersonelOdemeService personelOdemeService;

    public PersonelOdemeResource(PersonelOdemeService personelOdemeService) {
        this.personelOdemeService = personelOdemeService;
    }

    /**
     * POST  /personel-odemes : Create a new personelOdeme.
     *
     * @param personelOdeme the personelOdeme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personelOdeme, or with status 400 (Bad Request) if the personelOdeme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personel-odemes")
    public ResponseEntity<PersonelOdeme> createPersonelOdeme(@Valid @RequestBody PersonelOdeme personelOdeme) throws URISyntaxException {
        log.debug("REST request to save PersonelOdeme : {}", personelOdeme);
        if (personelOdeme.getId() != null) {
            throw new BadRequestAlertException("A new personelOdeme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonelOdeme result = personelOdemeService.save(personelOdeme);
        return ResponseEntity.created(new URI("/api/personel-odemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personel-odemes : Updates an existing personelOdeme.
     *
     * @param personelOdeme the personelOdeme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personelOdeme,
     * or with status 400 (Bad Request) if the personelOdeme is not valid,
     * or with status 500 (Internal Server Error) if the personelOdeme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personel-odemes")
    public ResponseEntity<PersonelOdeme> updatePersonelOdeme(@Valid @RequestBody PersonelOdeme personelOdeme) throws URISyntaxException {
        log.debug("REST request to update PersonelOdeme : {}", personelOdeme);
        if (personelOdeme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonelOdeme result = personelOdemeService.save(personelOdeme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personelOdeme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personel-odemes : get all the personelOdemes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personelOdemes in body
     */
    @GetMapping("/personel-odemes")
    public List<PersonelOdeme> getAllPersonelOdemes() {
        log.debug("REST request to get all PersonelOdemes");
        return personelOdemeService.findAll();
    }

    /**
     * GET  /personel-odemes/:id : get the "id" personelOdeme.
     *
     * @param id the id of the personelOdeme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personelOdeme, or with status 404 (Not Found)
     */
    @GetMapping("/personel-odemes/{id}")
    public ResponseEntity<PersonelOdeme> getPersonelOdeme(@PathVariable Long id) {
        log.debug("REST request to get PersonelOdeme : {}", id);
        Optional<PersonelOdeme> personelOdeme = personelOdemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personelOdeme);
    }

    /**
     * DELETE  /personel-odemes/:id : delete the "id" personelOdeme.
     *
     * @param id the id of the personelOdeme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personel-odemes/{id}")
    public ResponseEntity<Void> deletePersonelOdeme(@PathVariable Long id) {
        log.debug("REST request to delete PersonelOdeme : {}", id);
        personelOdemeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

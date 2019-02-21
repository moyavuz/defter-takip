package com.yavuzturtelekom.web.rest;
import com.yavuzturtelekom.domain.PersonelIzin;
import com.yavuzturtelekom.service.PersonelIzinService;
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
 * REST controller for managing PersonelIzin.
 */
@RestController
@RequestMapping("/api")
public class PersonelIzinResource {

    private final Logger log = LoggerFactory.getLogger(PersonelIzinResource.class);

    private static final String ENTITY_NAME = "personelIzin";

    private final PersonelIzinService personelIzinService;

    public PersonelIzinResource(PersonelIzinService personelIzinService) {
        this.personelIzinService = personelIzinService;
    }

    /**
     * POST  /personel-izins : Create a new personelIzin.
     *
     * @param personelIzin the personelIzin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personelIzin, or with status 400 (Bad Request) if the personelIzin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personel-izins")
    public ResponseEntity<PersonelIzin> createPersonelIzin(@Valid @RequestBody PersonelIzin personelIzin) throws URISyntaxException {
        log.debug("REST request to save PersonelIzin : {}", personelIzin);
        if (personelIzin.getId() != null) {
            throw new BadRequestAlertException("A new personelIzin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonelIzin result = personelIzinService.save(personelIzin);
        return ResponseEntity.created(new URI("/api/personel-izins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personel-izins : Updates an existing personelIzin.
     *
     * @param personelIzin the personelIzin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personelIzin,
     * or with status 400 (Bad Request) if the personelIzin is not valid,
     * or with status 500 (Internal Server Error) if the personelIzin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personel-izins")
    public ResponseEntity<PersonelIzin> updatePersonelIzin(@Valid @RequestBody PersonelIzin personelIzin) throws URISyntaxException {
        log.debug("REST request to update PersonelIzin : {}", personelIzin);
        if (personelIzin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonelIzin result = personelIzinService.save(personelIzin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personelIzin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personel-izins : get all the personelIzins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personelIzins in body
     */
    @GetMapping("/personel-izins")
    public List<PersonelIzin> getAllPersonelIzins() {
        log.debug("REST request to get all PersonelIzins");
        return personelIzinService.findAll();
    }

    /**
     * GET  /personel-izins/:id : get the "id" personelIzin.
     *
     * @param id the id of the personelIzin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personelIzin, or with status 404 (Not Found)
     */
    @GetMapping("/personel-izins/{id}")
    public ResponseEntity<PersonelIzin> getPersonelIzin(@PathVariable Long id) {
        log.debug("REST request to get PersonelIzin : {}", id);
        Optional<PersonelIzin> personelIzin = personelIzinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personelIzin);
    }

    /**
     * DELETE  /personel-izins/:id : delete the "id" personelIzin.
     *
     * @param id the id of the personelIzin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personel-izins/{id}")
    public ResponseEntity<Void> deletePersonelIzin(@PathVariable Long id) {
        log.debug("REST request to delete PersonelIzin : {}", id);
        personelIzinService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

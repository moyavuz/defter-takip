package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PersonelAracService;
import com.yavuzturtelekom.domain.PersonelArac;
import com.yavuzturtelekom.repository.PersonelAracRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PersonelArac.
 */
@Service
@Transactional
public class PersonelAracServiceImpl implements PersonelAracService {

    private final Logger log = LoggerFactory.getLogger(PersonelAracServiceImpl.class);

    private final PersonelAracRepository personelAracRepository;

    public PersonelAracServiceImpl(PersonelAracRepository personelAracRepository) {
        this.personelAracRepository = personelAracRepository;
    }

    /**
     * Save a personelArac.
     *
     * @param personelArac the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonelArac save(PersonelArac personelArac) {
        log.debug("Request to save PersonelArac : {}", personelArac);
        return personelAracRepository.save(personelArac);
    }

    /**
     * Get all the personelAracs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonelArac> findAll(Pageable pageable) {
        log.debug("Request to get all PersonelAracs");
        return personelAracRepository.findAll(pageable);
    }


    /**
     * Get one personelArac by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonelArac> findOne(Long id) {
        log.debug("Request to get PersonelArac : {}", id);
        return personelAracRepository.findById(id);
    }

    /**
     * Delete the personelArac by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonelArac : {}", id);
        personelAracRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PersonelZimmetService;
import com.yavuzturtelekom.domain.PersonelZimmet;
import com.yavuzturtelekom.repository.PersonelZimmetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PersonelZimmet.
 */
@Service
@Transactional
public class PersonelZimmetServiceImpl implements PersonelZimmetService {

    private final Logger log = LoggerFactory.getLogger(PersonelZimmetServiceImpl.class);

    private final PersonelZimmetRepository personelZimmetRepository;

    public PersonelZimmetServiceImpl(PersonelZimmetRepository personelZimmetRepository) {
        this.personelZimmetRepository = personelZimmetRepository;
    }

    /**
     * Save a personelZimmet.
     *
     * @param personelZimmet the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonelZimmet save(PersonelZimmet personelZimmet) {
        log.debug("Request to save PersonelZimmet : {}", personelZimmet);
        return personelZimmetRepository.save(personelZimmet);
    }

    /**
     * Get all the personelZimmets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonelZimmet> findAll() {
        log.debug("Request to get all PersonelZimmets");
        return personelZimmetRepository.findAll();
    }


    /**
     * Get one personelZimmet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonelZimmet> findOne(Long id) {
        log.debug("Request to get PersonelZimmet : {}", id);
        return personelZimmetRepository.findById(id);
    }

    /**
     * Delete the personelZimmet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonelZimmet : {}", id);        personelZimmetRepository.deleteById(id);
    }
}

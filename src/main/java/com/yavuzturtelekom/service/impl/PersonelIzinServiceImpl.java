package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PersonelIzinService;
import com.yavuzturtelekom.domain.PersonelIzin;
import com.yavuzturtelekom.repository.PersonelIzinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PersonelIzin.
 */
@Service
@Transactional
public class PersonelIzinServiceImpl implements PersonelIzinService {

    private final Logger log = LoggerFactory.getLogger(PersonelIzinServiceImpl.class);

    private final PersonelIzinRepository personelIzinRepository;

    public PersonelIzinServiceImpl(PersonelIzinRepository personelIzinRepository) {
        this.personelIzinRepository = personelIzinRepository;
    }

    /**
     * Save a personelIzin.
     *
     * @param personelIzin the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonelIzin save(PersonelIzin personelIzin) {
        log.debug("Request to save PersonelIzin : {}", personelIzin);
        return personelIzinRepository.save(personelIzin);
    }

    /**
     * Get all the personelIzins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonelIzin> findAll() {
        log.debug("Request to get all PersonelIzins");
        return personelIzinRepository.findAll();
    }


    /**
     * Get one personelIzin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonelIzin> findOne(Long id) {
        log.debug("Request to get PersonelIzin : {}", id);
        return personelIzinRepository.findById(id);
    }

    /**
     * Delete the personelIzin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonelIzin : {}", id);        personelIzinRepository.deleteById(id);
    }
}

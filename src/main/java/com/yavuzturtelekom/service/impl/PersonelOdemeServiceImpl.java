package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PersonelOdemeService;
import com.yavuzturtelekom.domain.PersonelOdeme;
import com.yavuzturtelekom.repository.PersonelOdemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PersonelOdeme.
 */
@Service
@Transactional
public class PersonelOdemeServiceImpl implements PersonelOdemeService {

    private final Logger log = LoggerFactory.getLogger(PersonelOdemeServiceImpl.class);

    private final PersonelOdemeRepository personelOdemeRepository;

    public PersonelOdemeServiceImpl(PersonelOdemeRepository personelOdemeRepository) {
        this.personelOdemeRepository = personelOdemeRepository;
    }

    /**
     * Save a personelOdeme.
     *
     * @param personelOdeme the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonelOdeme save(PersonelOdeme personelOdeme) {
        log.debug("Request to save PersonelOdeme : {}", personelOdeme);
        return personelOdemeRepository.save(personelOdeme);
    }

    /**
     * Get all the personelOdemes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonelOdeme> findAll() {
        log.debug("Request to get all PersonelOdemes");
        return personelOdemeRepository.findAll();
    }


    /**
     * Get one personelOdeme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PersonelOdeme> findOne(Long id) {
        log.debug("Request to get PersonelOdeme : {}", id);
        return personelOdemeRepository.findById(id);
    }

    /**
     * Delete the personelOdeme by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonelOdeme : {}", id);        personelOdemeRepository.deleteById(id);
    }
}

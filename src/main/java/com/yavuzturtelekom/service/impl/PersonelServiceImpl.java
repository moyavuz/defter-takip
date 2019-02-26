package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PersonelService;
import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.repository.PersonelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Personel.
 */
@Service
@Transactional
public class PersonelServiceImpl implements PersonelService {

    private final Logger log = LoggerFactory.getLogger(PersonelServiceImpl.class);

    private final PersonelRepository personelRepository;

    public PersonelServiceImpl(PersonelRepository personelRepository) {
        this.personelRepository = personelRepository;
    }

    /**
     * Save a personel.
     *
     * @param personel the entity to save
     * @return the persisted entity
     */
    @Override
    public Personel save(Personel personel) {
        log.debug("Request to save Personel : {}", personel);
        return personelRepository.save(personel);
    }

    /**
     * Get all the personels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Personel> findAll(Pageable pageable) {
        log.debug("Request to get all Personels");
        return personelRepository.findAll(pageable);
    }


    /**
     * Get one personel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Personel> findOne(Long id) {
        log.debug("Request to get Personel : {}", id);
        return personelRepository.findById(id);
    }

    /**
     * Delete the personel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Personel : {}", id);        personelRepository.deleteById(id);
    }
}

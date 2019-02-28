package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PozService;
import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.repository.PozRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Poz.
 */
@Service
@Transactional
public class PozServiceImpl implements PozService {

    private final Logger log = LoggerFactory.getLogger(PozServiceImpl.class);

    private final PozRepository pozRepository;

    public PozServiceImpl(PozRepository pozRepository) {
        this.pozRepository = pozRepository;
    }

    /**
     * Save a poz.
     *
     * @param poz the entity to save
     * @return the persisted entity
     */
    @Override
    public Poz save(Poz poz) {
        log.debug("Request to save Poz : {}", poz);
        return pozRepository.save(poz);
    }

    /**
     * Get all the pozs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Poz> findAll(Pageable pageable) {
        log.debug("Request to get all Pozs");
        return pozRepository.findAll(pageable);
    }


    /**
     * Get one poz by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Poz> findOne(Long id) {
        log.debug("Request to get Poz : {}", id);
        return pozRepository.findById(id);
    }

    /**
     * Delete the poz by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Poz : {}", id);
        pozRepository.deleteById(id);
    }
}

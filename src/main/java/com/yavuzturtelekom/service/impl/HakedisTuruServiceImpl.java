package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.HakedisTuruService;
import com.yavuzturtelekom.domain.HakedisTuru;
import com.yavuzturtelekom.repository.HakedisTuruRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HakedisTuru.
 */
@Service
@Transactional
public class HakedisTuruServiceImpl implements HakedisTuruService {

    private final Logger log = LoggerFactory.getLogger(HakedisTuruServiceImpl.class);

    private final HakedisTuruRepository hakedisTuruRepository;

    public HakedisTuruServiceImpl(HakedisTuruRepository hakedisTuruRepository) {
        this.hakedisTuruRepository = hakedisTuruRepository;
    }

    /**
     * Save a hakedisTuru.
     *
     * @param hakedisTuru the entity to save
     * @return the persisted entity
     */
    @Override
    public HakedisTuru save(HakedisTuru hakedisTuru) {
        log.debug("Request to save HakedisTuru : {}", hakedisTuru);
        return hakedisTuruRepository.save(hakedisTuru);
    }

    /**
     * Get all the hakedisTurus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HakedisTuru> findAll(Pageable pageable) {
        log.debug("Request to get all HakedisTurus");
        return hakedisTuruRepository.findAll(pageable);
    }


    /**
     * Get one hakedisTuru by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HakedisTuru> findOne(Long id) {
        log.debug("Request to get HakedisTuru : {}", id);
        return hakedisTuruRepository.findById(id);
    }

    /**
     * Delete the hakedisTuru by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HakedisTuru : {}", id);
        hakedisTuruRepository.deleteById(id);
    }
}

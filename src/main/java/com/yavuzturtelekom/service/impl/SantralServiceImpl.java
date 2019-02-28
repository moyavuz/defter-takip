package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.SantralService;
import com.yavuzturtelekom.domain.Santral;
import com.yavuzturtelekom.repository.SantralRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Santral.
 */
@Service
@Transactional
public class SantralServiceImpl implements SantralService {

    private final Logger log = LoggerFactory.getLogger(SantralServiceImpl.class);

    private final SantralRepository santralRepository;

    public SantralServiceImpl(SantralRepository santralRepository) {
        this.santralRepository = santralRepository;
    }

    /**
     * Save a santral.
     *
     * @param santral the entity to save
     * @return the persisted entity
     */
    @Override
    public Santral save(Santral santral) {
        log.debug("Request to save Santral : {}", santral);
        return santralRepository.save(santral);
    }

    /**
     * Get all the santrals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Santral> findAll(Pageable pageable) {
        log.debug("Request to get all Santrals");
        return santralRepository.findAll(pageable);
    }


    /**
     * Get one santral by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Santral> findOne(Long id) {
        log.debug("Request to get Santral : {}", id);
        return santralRepository.findById(id);
    }

    /**
     * Delete the santral by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Santral : {}", id);
        santralRepository.deleteById(id);
    }
}

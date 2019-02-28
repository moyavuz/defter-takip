package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.HakedisService;
import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.repository.HakedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Hakedis.
 */
@Service
@Transactional
public class HakedisServiceImpl implements HakedisService {

    private final Logger log = LoggerFactory.getLogger(HakedisServiceImpl.class);

    private final HakedisRepository hakedisRepository;

    public HakedisServiceImpl(HakedisRepository hakedisRepository) {
        this.hakedisRepository = hakedisRepository;
    }

    /**
     * Save a hakedis.
     *
     * @param hakedis the entity to save
     * @return the persisted entity
     */
    @Override
    public Hakedis save(Hakedis hakedis) {
        log.debug("Request to save Hakedis : {}", hakedis);
        return hakedisRepository.save(hakedis);
    }

    /**
     * Get all the hakedis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Hakedis> findAll(Pageable pageable) {
        log.debug("Request to get all Hakedis");
        return hakedisRepository.findAll(pageable);
    }


    /**
     * Get one hakedis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Hakedis> findOne(Long id) {
        log.debug("Request to get Hakedis : {}", id);
        return hakedisRepository.findById(id);
    }

    /**
     * Delete the hakedis by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hakedis : {}", id);
        hakedisRepository.deleteById(id);
    }
}

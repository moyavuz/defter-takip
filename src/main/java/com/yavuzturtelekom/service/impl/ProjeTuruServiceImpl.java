package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.ProjeTuruService;
import com.yavuzturtelekom.domain.ProjeTuru;
import com.yavuzturtelekom.repository.ProjeTuruRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ProjeTuru.
 */
@Service
@Transactional
public class ProjeTuruServiceImpl implements ProjeTuruService {

    private final Logger log = LoggerFactory.getLogger(ProjeTuruServiceImpl.class);

    private final ProjeTuruRepository projeTuruRepository;

    public ProjeTuruServiceImpl(ProjeTuruRepository projeTuruRepository) {
        this.projeTuruRepository = projeTuruRepository;
    }

    /**
     * Save a projeTuru.
     *
     * @param projeTuru the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjeTuru save(ProjeTuru projeTuru) {
        log.debug("Request to save ProjeTuru : {}", projeTuru);
        return projeTuruRepository.save(projeTuru);
    }

    /**
     * Get all the projeTurus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjeTuru> findAll() {
        log.debug("Request to get all ProjeTurus");
        return projeTuruRepository.findAll();
    }


    /**
     * Get one projeTuru by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjeTuru> findOne(Long id) {
        log.debug("Request to get ProjeTuru : {}", id);
        return projeTuruRepository.findById(id);
    }

    /**
     * Delete the projeTuru by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjeTuru : {}", id);        projeTuruRepository.deleteById(id);
    }
}

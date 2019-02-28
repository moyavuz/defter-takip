package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.EskalasyonService;
import com.yavuzturtelekom.domain.Eskalasyon;
import com.yavuzturtelekom.repository.EskalasyonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Eskalasyon.
 */
@Service
@Transactional
public class EskalasyonServiceImpl implements EskalasyonService {

    private final Logger log = LoggerFactory.getLogger(EskalasyonServiceImpl.class);

    private final EskalasyonRepository eskalasyonRepository;

    public EskalasyonServiceImpl(EskalasyonRepository eskalasyonRepository) {
        this.eskalasyonRepository = eskalasyonRepository;
    }

    /**
     * Save a eskalasyon.
     *
     * @param eskalasyon the entity to save
     * @return the persisted entity
     */
    @Override
    public Eskalasyon save(Eskalasyon eskalasyon) {
        log.debug("Request to save Eskalasyon : {}", eskalasyon);
        return eskalasyonRepository.save(eskalasyon);
    }

    /**
     * Get all the eskalasyons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Eskalasyon> findAll(Pageable pageable) {
        log.debug("Request to get all Eskalasyons");
        return eskalasyonRepository.findAll(pageable);
    }


    /**
     * Get one eskalasyon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Eskalasyon> findOne(Long id) {
        log.debug("Request to get Eskalasyon : {}", id);
        return eskalasyonRepository.findById(id);
    }

    /**
     * Delete the eskalasyon by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eskalasyon : {}", id);
        eskalasyonRepository.deleteById(id);
    }
}

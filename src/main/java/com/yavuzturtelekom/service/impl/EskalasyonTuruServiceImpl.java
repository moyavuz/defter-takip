package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.EskalasyonTuruService;
import com.yavuzturtelekom.domain.EskalasyonTuru;
import com.yavuzturtelekom.repository.EskalasyonTuruRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing EskalasyonTuru.
 */
@Service
@Transactional
public class EskalasyonTuruServiceImpl implements EskalasyonTuruService {

    private final Logger log = LoggerFactory.getLogger(EskalasyonTuruServiceImpl.class);

    private final EskalasyonTuruRepository eskalasyonTuruRepository;

    public EskalasyonTuruServiceImpl(EskalasyonTuruRepository eskalasyonTuruRepository) {
        this.eskalasyonTuruRepository = eskalasyonTuruRepository;
    }

    /**
     * Save a eskalasyonTuru.
     *
     * @param eskalasyonTuru the entity to save
     * @return the persisted entity
     */
    @Override
    public EskalasyonTuru save(EskalasyonTuru eskalasyonTuru) {
        log.debug("Request to save EskalasyonTuru : {}", eskalasyonTuru);
        return eskalasyonTuruRepository.save(eskalasyonTuru);
    }

    /**
     * Get all the eskalasyonTurus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EskalasyonTuru> findAll() {
        log.debug("Request to get all EskalasyonTurus");
        return eskalasyonTuruRepository.findAll();
    }


    /**
     * Get one eskalasyonTuru by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EskalasyonTuru> findOne(Long id) {
        log.debug("Request to get EskalasyonTuru : {}", id);
        return eskalasyonTuruRepository.findById(id);
    }

    /**
     * Delete the eskalasyonTuru by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EskalasyonTuru : {}", id);        eskalasyonTuruRepository.deleteById(id);
    }
}

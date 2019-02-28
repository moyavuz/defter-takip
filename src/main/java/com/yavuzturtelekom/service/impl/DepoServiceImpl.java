package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.DepoService;
import com.yavuzturtelekom.domain.Depo;
import com.yavuzturtelekom.repository.DepoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Depo.
 */
@Service
@Transactional
public class DepoServiceImpl implements DepoService {

    private final Logger log = LoggerFactory.getLogger(DepoServiceImpl.class);

    private final DepoRepository depoRepository;

    public DepoServiceImpl(DepoRepository depoRepository) {
        this.depoRepository = depoRepository;
    }

    /**
     * Save a depo.
     *
     * @param depo the entity to save
     * @return the persisted entity
     */
    @Override
    public Depo save(Depo depo) {
        log.debug("Request to save Depo : {}", depo);
        return depoRepository.save(depo);
    }

    /**
     * Get all the depos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Depo> findAll(Pageable pageable) {
        log.debug("Request to get all Depos");
        return depoRepository.findAll(pageable);
    }


    /**
     * Get one depo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Depo> findOne(Long id) {
        log.debug("Request to get Depo : {}", id);
        return depoRepository.findById(id);
    }

    /**
     * Delete the depo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Depo : {}", id);
        depoRepository.deleteById(id);
    }
}

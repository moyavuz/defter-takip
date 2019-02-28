package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.AracService;
import com.yavuzturtelekom.domain.Arac;
import com.yavuzturtelekom.repository.AracRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Arac.
 */
@Service
@Transactional
public class AracServiceImpl implements AracService {

    private final Logger log = LoggerFactory.getLogger(AracServiceImpl.class);

    private final AracRepository aracRepository;

    public AracServiceImpl(AracRepository aracRepository) {
        this.aracRepository = aracRepository;
    }

    /**
     * Save a arac.
     *
     * @param arac the entity to save
     * @return the persisted entity
     */
    @Override
    public Arac save(Arac arac) {
        log.debug("Request to save Arac : {}", arac);
        return aracRepository.save(arac);
    }

    /**
     * Get all the aracs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Arac> findAll(Pageable pageable) {
        log.debug("Request to get all Aracs");
        return aracRepository.findAll(pageable);
    }


    /**
     * Get one arac by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Arac> findOne(Long id) {
        log.debug("Request to get Arac : {}", id);
        return aracRepository.findById(id);
    }

    /**
     * Delete the arac by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Arac : {}", id);
        aracRepository.deleteById(id);
    }
}

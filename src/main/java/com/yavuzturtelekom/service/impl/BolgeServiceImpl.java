package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.BolgeService;
import com.yavuzturtelekom.domain.Bolge;
import com.yavuzturtelekom.repository.BolgeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Bolge.
 */
@Service
@Transactional
public class BolgeServiceImpl implements BolgeService {

    private final Logger log = LoggerFactory.getLogger(BolgeServiceImpl.class);

    private final BolgeRepository bolgeRepository;

    public BolgeServiceImpl(BolgeRepository bolgeRepository) {
        this.bolgeRepository = bolgeRepository;
    }

    /**
     * Save a bolge.
     *
     * @param bolge the entity to save
     * @return the persisted entity
     */
    @Override
    public Bolge save(Bolge bolge) {
        log.debug("Request to save Bolge : {}", bolge);
        return bolgeRepository.save(bolge);
    }

    /**
     * Get all the bolges.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Bolge> findAll() {
        log.debug("Request to get all Bolges");
        return bolgeRepository.findAll();
    }


    /**
     * Get one bolge by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Bolge> findOne(Long id) {
        log.debug("Request to get Bolge : {}", id);
        return bolgeRepository.findById(id);
    }

    /**
     * Delete the bolge by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bolge : {}", id);        bolgeRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.MarkaService;
import com.yavuzturtelekom.domain.Marka;
import com.yavuzturtelekom.repository.MarkaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Marka.
 */
@Service
@Transactional
public class MarkaServiceImpl implements MarkaService {

    private final Logger log = LoggerFactory.getLogger(MarkaServiceImpl.class);

    private final MarkaRepository markaRepository;

    public MarkaServiceImpl(MarkaRepository markaRepository) {
        this.markaRepository = markaRepository;
    }

    /**
     * Save a marka.
     *
     * @param marka the entity to save
     * @return the persisted entity
     */
    @Override
    public Marka save(Marka marka) {
        log.debug("Request to save Marka : {}", marka);
        return markaRepository.save(marka);
    }

    /**
     * Get all the markas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Marka> findAll() {
        log.debug("Request to get all Markas");
        return markaRepository.findAll();
    }


    /**
     * Get one marka by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Marka> findOne(Long id) {
        log.debug("Request to get Marka : {}", id);
        return markaRepository.findById(id);
    }

    /**
     * Delete the marka by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Marka : {}", id);        markaRepository.deleteById(id);
    }
}

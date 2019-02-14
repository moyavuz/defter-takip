package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.MalzemeTakipService;
import com.yavuzturtelekom.domain.MalzemeTakip;
import com.yavuzturtelekom.repository.MalzemeTakipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing MalzemeTakip.
 */
@Service
@Transactional
public class MalzemeTakipServiceImpl implements MalzemeTakipService {

    private final Logger log = LoggerFactory.getLogger(MalzemeTakipServiceImpl.class);

    private final MalzemeTakipRepository malzemeTakipRepository;

    public MalzemeTakipServiceImpl(MalzemeTakipRepository malzemeTakipRepository) {
        this.malzemeTakipRepository = malzemeTakipRepository;
    }

    /**
     * Save a malzemeTakip.
     *
     * @param malzemeTakip the entity to save
     * @return the persisted entity
     */
    @Override
    public MalzemeTakip save(MalzemeTakip malzemeTakip) {
        log.debug("Request to save MalzemeTakip : {}", malzemeTakip);
        return malzemeTakipRepository.save(malzemeTakip);
    }

    /**
     * Get all the malzemeTakips.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MalzemeTakip> findAll() {
        log.debug("Request to get all MalzemeTakips");
        return malzemeTakipRepository.findAll();
    }


    /**
     * Get one malzemeTakip by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MalzemeTakip> findOne(Long id) {
        log.debug("Request to get MalzemeTakip : {}", id);
        return malzemeTakipRepository.findById(id);
    }

    /**
     * Delete the malzemeTakip by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MalzemeTakip : {}", id);        malzemeTakipRepository.deleteById(id);
    }
}

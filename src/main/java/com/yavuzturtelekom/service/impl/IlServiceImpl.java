package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.IlService;
import com.yavuzturtelekom.domain.Il;
import com.yavuzturtelekom.repository.IlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Il.
 */
@Service
@Transactional
public class IlServiceImpl implements IlService {

    private final Logger log = LoggerFactory.getLogger(IlServiceImpl.class);

    private final IlRepository ilRepository;

    public IlServiceImpl(IlRepository ilRepository) {
        this.ilRepository = ilRepository;
    }

    /**
     * Save a il.
     *
     * @param il the entity to save
     * @return the persisted entity
     */
    @Override
    public Il save(Il il) {
        log.debug("Request to save Il : {}", il);
        return ilRepository.save(il);
    }

    /**
     * Get all the ils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Il> findAll(Pageable pageable) {
        log.debug("Request to get all Ils");
        return ilRepository.findAll(pageable);
    }


    /**
     * Get one il by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Il> findOne(Long id) {
        log.debug("Request to get Il : {}", id);
        return ilRepository.findById(id);
    }

    /**
     * Delete the il by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Il : {}", id);
        ilRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.UnvanService;
import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.repository.UnvanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Unvan.
 */
@Service
@Transactional
public class UnvanServiceImpl implements UnvanService {

    private final Logger log = LoggerFactory.getLogger(UnvanServiceImpl.class);

    private final UnvanRepository unvanRepository;

    public UnvanServiceImpl(UnvanRepository unvanRepository) {
        this.unvanRepository = unvanRepository;
    }

    /**
     * Save a unvan.
     *
     * @param unvan the entity to save
     * @return the persisted entity
     */
    @Override
    public Unvan save(Unvan unvan) {
        log.debug("Request to save Unvan : {}", unvan);
        return unvanRepository.save(unvan);
    }

    /**
     * Get all the unvans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Unvan> findAll(Pageable pageable) {
        log.debug("Request to get all Unvans");
        return unvanRepository.findAll(pageable);
    }


    /**
     * Get one unvan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Unvan> findOne(Long id) {
        log.debug("Request to get Unvan : {}", id);
        return unvanRepository.findById(id);
    }

    /**
     * Delete the unvan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unvan : {}", id);
        unvanRepository.deleteById(id);
    }
}

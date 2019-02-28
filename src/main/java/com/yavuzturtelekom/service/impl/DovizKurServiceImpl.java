package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.DovizKurService;
import com.yavuzturtelekom.domain.DovizKur;
import com.yavuzturtelekom.repository.DovizKurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DovizKur.
 */
@Service
@Transactional
public class DovizKurServiceImpl implements DovizKurService {

    private final Logger log = LoggerFactory.getLogger(DovizKurServiceImpl.class);

    private final DovizKurRepository dovizKurRepository;

    public DovizKurServiceImpl(DovizKurRepository dovizKurRepository) {
        this.dovizKurRepository = dovizKurRepository;
    }

    /**
     * Save a dovizKur.
     *
     * @param dovizKur the entity to save
     * @return the persisted entity
     */
    @Override
    public DovizKur save(DovizKur dovizKur) {
        log.debug("Request to save DovizKur : {}", dovizKur);
        return dovizKurRepository.save(dovizKur);
    }

    /**
     * Get all the dovizKurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DovizKur> findAll(Pageable pageable) {
        log.debug("Request to get all DovizKurs");
        return dovizKurRepository.findAll(pageable);
    }


    /**
     * Get one dovizKur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DovizKur> findOne(Long id) {
        log.debug("Request to get DovizKur : {}", id);
        return dovizKurRepository.findById(id);
    }

    /**
     * Delete the dovizKur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DovizKur : {}", id);
        dovizKurRepository.deleteById(id);
    }
}

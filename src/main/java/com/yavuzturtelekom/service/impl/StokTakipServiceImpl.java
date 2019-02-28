package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.StokTakipService;
import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.repository.StokTakipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing StokTakip.
 */
@Service
@Transactional
public class StokTakipServiceImpl implements StokTakipService {

    private final Logger log = LoggerFactory.getLogger(StokTakipServiceImpl.class);

    private final StokTakipRepository stokTakipRepository;

    public StokTakipServiceImpl(StokTakipRepository stokTakipRepository) {
        this.stokTakipRepository = stokTakipRepository;
    }

    /**
     * Save a stokTakip.
     *
     * @param stokTakip the entity to save
     * @return the persisted entity
     */
    @Override
    public StokTakip save(StokTakip stokTakip) {
        log.debug("Request to save StokTakip : {}", stokTakip);
        return stokTakipRepository.save(stokTakip);
    }

    /**
     * Get all the stokTakips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StokTakip> findAll(Pageable pageable) {
        log.debug("Request to get all StokTakips");
        return stokTakipRepository.findAll(pageable);
    }


    /**
     * Get one stokTakip by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StokTakip> findOne(Long id) {
        log.debug("Request to get StokTakip : {}", id);
        return stokTakipRepository.findById(id);
    }

    /**
     * Delete the stokTakip by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StokTakip : {}", id);
        stokTakipRepository.deleteById(id);
    }
}

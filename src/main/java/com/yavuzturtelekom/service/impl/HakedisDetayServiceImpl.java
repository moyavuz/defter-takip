package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.HakedisDetayService;
import com.yavuzturtelekom.domain.HakedisDetay;
import com.yavuzturtelekom.repository.HakedisDetayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing HakedisDetay.
 */
@Service
@Transactional
public class HakedisDetayServiceImpl implements HakedisDetayService {

    private final Logger log = LoggerFactory.getLogger(HakedisDetayServiceImpl.class);

    private final HakedisDetayRepository hakedisDetayRepository;

    public HakedisDetayServiceImpl(HakedisDetayRepository hakedisDetayRepository) {
        this.hakedisDetayRepository = hakedisDetayRepository;
    }

    /**
     * Save a hakedisDetay.
     *
     * @param hakedisDetay the entity to save
     * @return the persisted entity
     */
    @Override
    public HakedisDetay save(HakedisDetay hakedisDetay) {
        log.debug("Request to save HakedisDetay : {}", hakedisDetay);
        return hakedisDetayRepository.save(hakedisDetay);
    }

    /**
     * Get all the hakedisDetays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HakedisDetay> findAll(Pageable pageable) {
        log.debug("Request to get all HakedisDetays");
        return hakedisDetayRepository.findAll(pageable);
    }


    /**
     * Get one hakedisDetay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HakedisDetay> findOne(Long id) {
        log.debug("Request to get HakedisDetay : {}", id);
        return hakedisDetayRepository.findById(id);
    }

    /**
     * Delete the hakedisDetay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HakedisDetay : {}", id);        hakedisDetayRepository.deleteById(id);
    }
}

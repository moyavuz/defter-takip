package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.ZimmetTuruService;
import com.yavuzturtelekom.domain.ZimmetTuru;
import com.yavuzturtelekom.repository.ZimmetTuruRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ZimmetTuru.
 */
@Service
@Transactional
public class ZimmetTuruServiceImpl implements ZimmetTuruService {

    private final Logger log = LoggerFactory.getLogger(ZimmetTuruServiceImpl.class);

    private final ZimmetTuruRepository zimmetTuruRepository;

    public ZimmetTuruServiceImpl(ZimmetTuruRepository zimmetTuruRepository) {
        this.zimmetTuruRepository = zimmetTuruRepository;
    }

    /**
     * Save a zimmetTuru.
     *
     * @param zimmetTuru the entity to save
     * @return the persisted entity
     */
    @Override
    public ZimmetTuru save(ZimmetTuru zimmetTuru) {
        log.debug("Request to save ZimmetTuru : {}", zimmetTuru);
        return zimmetTuruRepository.save(zimmetTuru);
    }

    /**
     * Get all the zimmetTurus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZimmetTuru> findAll() {
        log.debug("Request to get all ZimmetTurus");
        return zimmetTuruRepository.findAll();
    }


    /**
     * Get one zimmetTuru by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ZimmetTuru> findOne(Long id) {
        log.debug("Request to get ZimmetTuru : {}", id);
        return zimmetTuruRepository.findById(id);
    }

    /**
     * Delete the zimmetTuru by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ZimmetTuru : {}", id);        zimmetTuruRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.DefterTuruService;
import com.yavuzturtelekom.domain.DefterTuru;
import com.yavuzturtelekom.repository.DefterTuruRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DefterTuru.
 */
@Service
@Transactional
public class DefterTuruServiceImpl implements DefterTuruService {

    private final Logger log = LoggerFactory.getLogger(DefterTuruServiceImpl.class);

    private final DefterTuruRepository defterTuruRepository;

    public DefterTuruServiceImpl(DefterTuruRepository defterTuruRepository) {
        this.defterTuruRepository = defterTuruRepository;
    }

    /**
     * Save a defterTuru.
     *
     * @param defterTuru the entity to save
     * @return the persisted entity
     */
    @Override
    public DefterTuru save(DefterTuru defterTuru) {
        log.debug("Request to save DefterTuru : {}", defterTuru);
        return defterTuruRepository.save(defterTuru);
    }

    /**
     * Get all the defterTurus.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DefterTuru> findAll() {
        log.debug("Request to get all DefterTurus");
        return defterTuruRepository.findAll();
    }


    /**
     * Get one defterTuru by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DefterTuru> findOne(Long id) {
        log.debug("Request to get DefterTuru : {}", id);
        return defterTuruRepository.findById(id);
    }

    /**
     * Delete the defterTuru by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DefterTuru : {}", id);        defterTuruRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.BirimService;
import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.repository.BirimRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Birim.
 */
@Service
@Transactional
public class BirimServiceImpl implements BirimService {

    private final Logger log = LoggerFactory.getLogger(BirimServiceImpl.class);

    private final BirimRepository birimRepository;

    public BirimServiceImpl(BirimRepository birimRepository) {
        this.birimRepository = birimRepository;
    }

    /**
     * Save a birim.
     *
     * @param birim the entity to save
     * @return the persisted entity
     */
    @Override
    public Birim save(Birim birim) {
        log.debug("Request to save Birim : {}", birim);
        return birimRepository.save(birim);
    }

    /**
     * Get all the birims.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Birim> findAll() {
        log.debug("Request to get all Birims");
        return birimRepository.findAll();
    }


    /**
     * Get one birim by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Birim> findOne(Long id) {
        log.debug("Request to get Birim : {}", id);
        return birimRepository.findById(id);
    }

    /**
     * Delete the birim by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Birim : {}", id);        birimRepository.deleteById(id);
    }
}

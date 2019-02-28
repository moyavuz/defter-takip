package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.PozGrubuService;
import com.yavuzturtelekom.domain.PozGrubu;
import com.yavuzturtelekom.repository.PozGrubuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PozGrubu.
 */
@Service
@Transactional
public class PozGrubuServiceImpl implements PozGrubuService {

    private final Logger log = LoggerFactory.getLogger(PozGrubuServiceImpl.class);

    private final PozGrubuRepository pozGrubuRepository;

    public PozGrubuServiceImpl(PozGrubuRepository pozGrubuRepository) {
        this.pozGrubuRepository = pozGrubuRepository;
    }

    /**
     * Save a pozGrubu.
     *
     * @param pozGrubu the entity to save
     * @return the persisted entity
     */
    @Override
    public PozGrubu save(PozGrubu pozGrubu) {
        log.debug("Request to save PozGrubu : {}", pozGrubu);
        return pozGrubuRepository.save(pozGrubu);
    }

    /**
     * Get all the pozGrubus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PozGrubu> findAll(Pageable pageable) {
        log.debug("Request to get all PozGrubus");
        return pozGrubuRepository.findAll(pageable);
    }

    /**
     * Get all the PozGrubu with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PozGrubu> findAllWithEagerRelationships(Pageable pageable) {
        return pozGrubuRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one pozGrubu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PozGrubu> findOne(Long id) {
        log.debug("Request to get PozGrubu : {}", id);
        return pozGrubuRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the pozGrubu by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PozGrubu : {}", id);
        pozGrubuRepository.deleteById(id);
    }
}

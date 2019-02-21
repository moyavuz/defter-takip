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

import java.util.List;
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
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PozGrubu> findAll() {
        log.debug("Request to get all PozGrubus");
        return pozGrubuRepository.findAllWithEagerRelationships();
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
        log.debug("Request to delete PozGrubu : {}", id);        pozGrubuRepository.deleteById(id);
    }
}

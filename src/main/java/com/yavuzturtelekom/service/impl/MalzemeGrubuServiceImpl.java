package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.MalzemeGrubuService;
import com.yavuzturtelekom.domain.MalzemeGrubu;
import com.yavuzturtelekom.repository.MalzemeGrubuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MalzemeGrubu.
 */
@Service
@Transactional
public class MalzemeGrubuServiceImpl implements MalzemeGrubuService {

    private final Logger log = LoggerFactory.getLogger(MalzemeGrubuServiceImpl.class);

    private final MalzemeGrubuRepository malzemeGrubuRepository;

    public MalzemeGrubuServiceImpl(MalzemeGrubuRepository malzemeGrubuRepository) {
        this.malzemeGrubuRepository = malzemeGrubuRepository;
    }

    /**
     * Save a malzemeGrubu.
     *
     * @param malzemeGrubu the entity to save
     * @return the persisted entity
     */
    @Override
    public MalzemeGrubu save(MalzemeGrubu malzemeGrubu) {
        log.debug("Request to save MalzemeGrubu : {}", malzemeGrubu);
        return malzemeGrubuRepository.save(malzemeGrubu);
    }

    /**
     * Get all the malzemeGrubus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MalzemeGrubu> findAll(Pageable pageable) {
        log.debug("Request to get all MalzemeGrubus");
        return malzemeGrubuRepository.findAll(pageable);
    }

    /**
     * Get all the MalzemeGrubu with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MalzemeGrubu> findAllWithEagerRelationships(Pageable pageable) {
        return malzemeGrubuRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one malzemeGrubu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MalzemeGrubu> findOne(Long id) {
        log.debug("Request to get MalzemeGrubu : {}", id);
        return malzemeGrubuRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the malzemeGrubu by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MalzemeGrubu : {}", id);
        malzemeGrubuRepository.deleteById(id);
    }
}

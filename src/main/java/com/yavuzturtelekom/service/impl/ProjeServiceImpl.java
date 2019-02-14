package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.ProjeService;
import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.repository.ProjeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Proje.
 */
@Service
@Transactional
public class ProjeServiceImpl implements ProjeService {

    private final Logger log = LoggerFactory.getLogger(ProjeServiceImpl.class);

    private final ProjeRepository projeRepository;

    public ProjeServiceImpl(ProjeRepository projeRepository) {
        this.projeRepository = projeRepository;
    }

    /**
     * Save a proje.
     *
     * @param proje the entity to save
     * @return the persisted entity
     */
    @Override
    public Proje save(Proje proje) {
        log.debug("Request to save Proje : {}", proje);
        return projeRepository.save(proje);
    }

    /**
     * Get all the projes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Proje> findAll(Pageable pageable) {
        log.debug("Request to get all Projes");
        return projeRepository.findAll(pageable);
    }

    /**
     * Get all the Proje with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Proje> findAllWithEagerRelationships(Pageable pageable) {
        return projeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one proje by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Proje> findOne(Long id) {
        log.debug("Request to get Proje : {}", id);
        return projeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the proje by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proje : {}", id);        projeRepository.deleteById(id);
    }
}

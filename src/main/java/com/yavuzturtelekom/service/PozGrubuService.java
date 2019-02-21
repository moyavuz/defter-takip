package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PozGrubu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PozGrubu.
 */
public interface PozGrubuService {

    /**
     * Save a pozGrubu.
     *
     * @param pozGrubu the entity to save
     * @return the persisted entity
     */
    PozGrubu save(PozGrubu pozGrubu);

    /**
     * Get all the pozGrubus.
     *
     * @return the list of entities
     */
    List<PozGrubu> findAll();

    /**
     * Get all the PozGrubu with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PozGrubu> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" pozGrubu.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PozGrubu> findOne(Long id);

    /**
     * Delete the "id" pozGrubu.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

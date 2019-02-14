package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.MalzemeGrubu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing MalzemeGrubu.
 */
public interface MalzemeGrubuService {

    /**
     * Save a malzemeGrubu.
     *
     * @param malzemeGrubu the entity to save
     * @return the persisted entity
     */
    MalzemeGrubu save(MalzemeGrubu malzemeGrubu);

    /**
     * Get all the malzemeGrubus.
     *
     * @return the list of entities
     */
    List<MalzemeGrubu> findAll();

    /**
     * Get all the MalzemeGrubu with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<MalzemeGrubu> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" malzemeGrubu.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MalzemeGrubu> findOne(Long id);

    /**
     * Delete the "id" malzemeGrubu.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

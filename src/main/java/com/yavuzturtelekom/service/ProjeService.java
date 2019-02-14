package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Proje;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Proje.
 */
public interface ProjeService {

    /**
     * Save a proje.
     *
     * @param proje the entity to save
     * @return the persisted entity
     */
    Proje save(Proje proje);

    /**
     * Get all the projes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Proje> findAll(Pageable pageable);

    /**
     * Get all the Proje with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Proje> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" proje.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Proje> findOne(Long id);

    /**
     * Delete the "id" proje.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

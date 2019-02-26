package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Poz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Poz.
 */
public interface PozService {

    /**
     * Save a poz.
     *
     * @param poz the entity to save
     * @return the persisted entity
     */
    Poz save(Poz poz);

    /**
     * Get all the pozs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Poz> findAll(Pageable pageable);


    /**
     * Get the "id" poz.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Poz> findOne(Long id);

    /**
     * Delete the "id" poz.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

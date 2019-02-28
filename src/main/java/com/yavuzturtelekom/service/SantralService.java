package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Santral;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Santral.
 */
public interface SantralService {

    /**
     * Save a santral.
     *
     * @param santral the entity to save
     * @return the persisted entity
     */
    Santral save(Santral santral);

    /**
     * Get all the santrals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Santral> findAll(Pageable pageable);


    /**
     * Get the "id" santral.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Santral> findOne(Long id);

    /**
     * Delete the "id" santral.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Santral;

import java.util.List;
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
     * @return the list of entities
     */
    List<Santral> findAll();


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

package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.HakedisTuru;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing HakedisTuru.
 */
public interface HakedisTuruService {

    /**
     * Save a hakedisTuru.
     *
     * @param hakedisTuru the entity to save
     * @return the persisted entity
     */
    HakedisTuru save(HakedisTuru hakedisTuru);

    /**
     * Get all the hakedisTurus.
     *
     * @return the list of entities
     */
    List<HakedisTuru> findAll();


    /**
     * Get the "id" hakedisTuru.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HakedisTuru> findOne(Long id);

    /**
     * Delete the "id" hakedisTuru.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

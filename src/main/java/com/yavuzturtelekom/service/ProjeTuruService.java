package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.ProjeTuru;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ProjeTuru.
 */
public interface ProjeTuruService {

    /**
     * Save a projeTuru.
     *
     * @param projeTuru the entity to save
     * @return the persisted entity
     */
    ProjeTuru save(ProjeTuru projeTuru);

    /**
     * Get all the projeTurus.
     *
     * @return the list of entities
     */
    List<ProjeTuru> findAll();


    /**
     * Get the "id" projeTuru.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProjeTuru> findOne(Long id);

    /**
     * Delete the "id" projeTuru.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

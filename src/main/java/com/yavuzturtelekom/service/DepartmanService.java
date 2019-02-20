package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Departman;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Departman.
 */
public interface DepartmanService {

    /**
     * Save a departman.
     *
     * @param departman the entity to save
     * @return the persisted entity
     */
    Departman save(Departman departman);

    /**
     * Get all the departmen.
     *
     * @return the list of entities
     */
    List<Departman> findAll();


    /**
     * Get the "id" departman.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Departman> findOne(Long id);

    /**
     * Delete the "id" departman.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

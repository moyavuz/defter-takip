package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Malzeme;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Malzeme.
 */
public interface MalzemeService {

    /**
     * Save a malzeme.
     *
     * @param malzeme the entity to save
     * @return the persisted entity
     */
    Malzeme save(Malzeme malzeme);

    /**
     * Get all the malzemes.
     *
     * @return the list of entities
     */
    List<Malzeme> findAll();


    /**
     * Get the "id" malzeme.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Malzeme> findOne(Long id);

    /**
     * Delete the "id" malzeme.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

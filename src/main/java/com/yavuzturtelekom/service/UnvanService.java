package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Unvan;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Unvan.
 */
public interface UnvanService {

    /**
     * Save a unvan.
     *
     * @param unvan the entity to save
     * @return the persisted entity
     */
    Unvan save(Unvan unvan);

    /**
     * Get all the unvans.
     *
     * @return the list of entities
     */
    List<Unvan> findAll();


    /**
     * Get the "id" unvan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Unvan> findOne(Long id);

    /**
     * Delete the "id" unvan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

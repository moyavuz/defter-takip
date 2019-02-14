package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Bolge;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Bolge.
 */
public interface BolgeService {

    /**
     * Save a bolge.
     *
     * @param bolge the entity to save
     * @return the persisted entity
     */
    Bolge save(Bolge bolge);

    /**
     * Get all the bolges.
     *
     * @return the list of entities
     */
    List<Bolge> findAll();


    /**
     * Get the "id" bolge.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Bolge> findOne(Long id);

    /**
     * Delete the "id" bolge.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

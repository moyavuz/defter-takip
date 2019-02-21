package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Birim;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Birim.
 */
public interface BirimService {

    /**
     * Save a birim.
     *
     * @param birim the entity to save
     * @return the persisted entity
     */
    Birim save(Birim birim);

    /**
     * Get all the birims.
     *
     * @return the list of entities
     */
    List<Birim> findAll();


    /**
     * Get the "id" birim.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Birim> findOne(Long id);

    /**
     * Delete the "id" birim.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

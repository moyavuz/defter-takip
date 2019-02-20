package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Marka;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Marka.
 */
public interface MarkaService {

    /**
     * Save a marka.
     *
     * @param marka the entity to save
     * @return the persisted entity
     */
    Marka save(Marka marka);

    /**
     * Get all the markas.
     *
     * @return the list of entities
     */
    List<Marka> findAll();


    /**
     * Get the "id" marka.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Marka> findOne(Long id);

    /**
     * Delete the "id" marka.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

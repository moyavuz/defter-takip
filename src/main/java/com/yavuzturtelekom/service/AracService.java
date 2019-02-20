package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Arac;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Arac.
 */
public interface AracService {

    /**
     * Save a arac.
     *
     * @param arac the entity to save
     * @return the persisted entity
     */
    Arac save(Arac arac);

    /**
     * Get all the aracs.
     *
     * @return the list of entities
     */
    List<Arac> findAll();


    /**
     * Get the "id" arac.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Arac> findOne(Long id);

    /**
     * Delete the "id" arac.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Depo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Depo.
 */
public interface DepoService {

    /**
     * Save a depo.
     *
     * @param depo the entity to save
     * @return the persisted entity
     */
    Depo save(Depo depo);

    /**
     * Get all the depos.
     *
     * @return the list of entities
     */
    List<Depo> findAll();


    /**
     * Get the "id" depo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Depo> findOne(Long id);

    /**
     * Delete the "id" depo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

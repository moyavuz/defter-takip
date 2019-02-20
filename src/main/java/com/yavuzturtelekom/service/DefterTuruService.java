package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.DefterTuru;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DefterTuru.
 */
public interface DefterTuruService {

    /**
     * Save a defterTuru.
     *
     * @param defterTuru the entity to save
     * @return the persisted entity
     */
    DefterTuru save(DefterTuru defterTuru);

    /**
     * Get all the defterTurus.
     *
     * @return the list of entities
     */
    List<DefterTuru> findAll();


    /**
     * Get the "id" defterTuru.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DefterTuru> findOne(Long id);

    /**
     * Delete the "id" defterTuru.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

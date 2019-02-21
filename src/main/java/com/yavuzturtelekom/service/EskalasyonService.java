package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Eskalasyon;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Eskalasyon.
 */
public interface EskalasyonService {

    /**
     * Save a eskalasyon.
     *
     * @param eskalasyon the entity to save
     * @return the persisted entity
     */
    Eskalasyon save(Eskalasyon eskalasyon);

    /**
     * Get all the eskalasyons.
     *
     * @return the list of entities
     */
    List<Eskalasyon> findAll();


    /**
     * Get the "id" eskalasyon.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Eskalasyon> findOne(Long id);

    /**
     * Delete the "id" eskalasyon.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

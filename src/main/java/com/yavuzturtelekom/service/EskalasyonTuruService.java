package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.EskalasyonTuru;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EskalasyonTuru.
 */
public interface EskalasyonTuruService {

    /**
     * Save a eskalasyonTuru.
     *
     * @param eskalasyonTuru the entity to save
     * @return the persisted entity
     */
    EskalasyonTuru save(EskalasyonTuru eskalasyonTuru);

    /**
     * Get all the eskalasyonTurus.
     *
     * @return the list of entities
     */
    List<EskalasyonTuru> findAll();


    /**
     * Get the "id" eskalasyonTuru.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EskalasyonTuru> findOne(Long id);

    /**
     * Delete the "id" eskalasyonTuru.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

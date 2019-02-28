package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Eskalasyon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Eskalasyon> findAll(Pageable pageable);


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

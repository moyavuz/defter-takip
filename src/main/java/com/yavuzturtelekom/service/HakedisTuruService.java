package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.HakedisTuru;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HakedisTuru.
 */
public interface HakedisTuruService {

    /**
     * Save a hakedisTuru.
     *
     * @param hakedisTuru the entity to save
     * @return the persisted entity
     */
    HakedisTuru save(HakedisTuru hakedisTuru);

    /**
     * Get all the hakedisTurus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HakedisTuru> findAll(Pageable pageable);


    /**
     * Get the "id" hakedisTuru.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HakedisTuru> findOne(Long id);

    /**
     * Delete the "id" hakedisTuru.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

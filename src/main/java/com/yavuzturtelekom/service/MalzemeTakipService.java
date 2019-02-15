package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.MalzemeTakip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MalzemeTakip.
 */
public interface MalzemeTakipService {

    /**
     * Save a malzemeTakip.
     *
     * @param malzemeTakip the entity to save
     * @return the persisted entity
     */
    MalzemeTakip save(MalzemeTakip malzemeTakip);

    /**
     * Get all the malzemeTakips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MalzemeTakip> findAll(Pageable pageable);


    /**
     * Get the "id" malzemeTakip.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MalzemeTakip> findOne(Long id);

    /**
     * Delete the "id" malzemeTakip.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

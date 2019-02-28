package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Arac;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Arac> findAll(Pageable pageable);


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

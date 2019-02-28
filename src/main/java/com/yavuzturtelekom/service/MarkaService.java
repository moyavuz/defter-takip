package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Marka;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Marka> findAll(Pageable pageable);


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

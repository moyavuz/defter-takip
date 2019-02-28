package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Il;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Il.
 */
public interface IlService {

    /**
     * Save a il.
     *
     * @param il the entity to save
     * @return the persisted entity
     */
    Il save(Il il);

    /**
     * Get all the ils.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Il> findAll(Pageable pageable);


    /**
     * Get the "id" il.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Il> findOne(Long id);

    /**
     * Delete the "id" il.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

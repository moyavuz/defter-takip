package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Birim;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Birim.
 */
public interface BirimService {

    /**
     * Save a birim.
     *
     * @param birim the entity to save
     * @return the persisted entity
     */
    Birim save(Birim birim);

    /**
     * Get all the birims.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Birim> findAll(Pageable pageable);


    /**
     * Get the "id" birim.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Birim> findOne(Long id);

    /**
     * Delete the "id" birim.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

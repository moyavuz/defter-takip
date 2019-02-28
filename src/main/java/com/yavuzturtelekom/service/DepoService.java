package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Depo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Depo.
 */
public interface DepoService {

    /**
     * Save a depo.
     *
     * @param depo the entity to save
     * @return the persisted entity
     */
    Depo save(Depo depo);

    /**
     * Get all the depos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Depo> findAll(Pageable pageable);


    /**
     * Get the "id" depo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Depo> findOne(Long id);

    /**
     * Delete the "id" depo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

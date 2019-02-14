package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Iscilik;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Iscilik.
 */
public interface IscilikService {

    /**
     * Save a iscilik.
     *
     * @param iscilik the entity to save
     * @return the persisted entity
     */
    Iscilik save(Iscilik iscilik);

    /**
     * Get all the isciliks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Iscilik> findAll(Pageable pageable);


    /**
     * Get the "id" iscilik.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Iscilik> findOne(Long id);

    /**
     * Delete the "id" iscilik.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

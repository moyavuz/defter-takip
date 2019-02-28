package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Ekip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Ekip.
 */
public interface EkipService {

    /**
     * Save a ekip.
     *
     * @param ekip the entity to save
     * @return the persisted entity
     */
    Ekip save(Ekip ekip);

    /**
     * Get all the ekips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Ekip> findAll(Pageable pageable);

    /**
     * Get all the Ekip with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Ekip> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" ekip.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Ekip> findOne(Long id);

    /**
     * Delete the "id" ekip.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

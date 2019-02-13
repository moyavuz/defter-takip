package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Ekip;

import java.util.List;
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
     * @return the list of entities
     */
    List<Ekip> findAll();


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

package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.DovizKur;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DovizKur.
 */
public interface DovizKurService {

    /**
     * Save a dovizKur.
     *
     * @param dovizKur the entity to save
     * @return the persisted entity
     */
    DovizKur save(DovizKur dovizKur);

    /**
     * Get all the dovizKurs.
     *
     * @return the list of entities
     */
    List<DovizKur> findAll();


    /**
     * Get the "id" dovizKur.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DovizKur> findOne(Long id);

    /**
     * Delete the "id" dovizKur.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

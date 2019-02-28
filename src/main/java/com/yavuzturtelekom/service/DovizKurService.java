package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.DovizKur;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DovizKur> findAll(Pageable pageable);


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

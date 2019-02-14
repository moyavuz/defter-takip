package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Malzeme;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Malzeme.
 */
public interface MalzemeService {

    /**
     * Save a malzeme.
     *
     * @param malzeme the entity to save
     * @return the persisted entity
     */
    Malzeme save(Malzeme malzeme);

    /**
     * Get all the malzemes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Malzeme> findAll(Pageable pageable);
    /**
     * Get all the MalzemeDTO where Poz is null.
     *
     * @return the list of entities
     */
    List<Malzeme> findAllWherePozIsNull();

    /**
     * Get all the Malzeme with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Malzeme> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" malzeme.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Malzeme> findOne(Long id);

    /**
     * Delete the "id" malzeme.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Hakedis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Hakedis.
 */
public interface HakedisService {

    /**
     * Save a hakedis.
     *
     * @param hakedis the entity to save
     * @return the persisted entity
     */
    Hakedis save(Hakedis hakedis);

    /**
     * Get all the hakedis.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Hakedis> findAll(Pageable pageable);


    /**
     * Get the "id" hakedis.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Hakedis> findOne(Long id);

    /**
     * Delete the "id" hakedis.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

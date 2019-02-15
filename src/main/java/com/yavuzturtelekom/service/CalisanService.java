package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Calisan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Calisan.
 */
public interface CalisanService {

    /**
     * Save a calisan.
     *
     * @param calisan the entity to save
     * @return the persisted entity
     */
    Calisan save(Calisan calisan);

    /**
     * Get all the calisans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Calisan> findAll(Pageable pageable);


    /**
     * Get the "id" calisan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Calisan> findOne(Long id);

    /**
     * Delete the "id" calisan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

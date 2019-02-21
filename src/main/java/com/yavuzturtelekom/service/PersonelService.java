package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Personel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Personel.
 */
public interface PersonelService {

    /**
     * Save a personel.
     *
     * @param personel the entity to save
     * @return the persisted entity
     */
    Personel save(Personel personel);

    /**
     * Get all the personels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Personel> findAll(Pageable pageable);


    /**
     * Get the "id" personel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Personel> findOne(Long id);

    /**
     * Delete the "id" personel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

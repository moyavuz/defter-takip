package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PersonelArac;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PersonelArac.
 */
public interface PersonelAracService {

    /**
     * Save a personelArac.
     *
     * @param personelArac the entity to save
     * @return the persisted entity
     */
    PersonelArac save(PersonelArac personelArac);

    /**
     * Get all the personelAracs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PersonelArac> findAll(Pageable pageable);


    /**
     * Get the "id" personelArac.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonelArac> findOne(Long id);

    /**
     * Delete the "id" personelArac.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

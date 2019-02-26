package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PersonelArac;

import java.util.List;
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
     * @return the list of entities
     */
    List<PersonelArac> findAll();


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

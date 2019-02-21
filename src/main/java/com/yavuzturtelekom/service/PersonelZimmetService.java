package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PersonelZimmet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PersonelZimmet.
 */
public interface PersonelZimmetService {

    /**
     * Save a personelZimmet.
     *
     * @param personelZimmet the entity to save
     * @return the persisted entity
     */
    PersonelZimmet save(PersonelZimmet personelZimmet);

    /**
     * Get all the personelZimmets.
     *
     * @return the list of entities
     */
    List<PersonelZimmet> findAll();


    /**
     * Get the "id" personelZimmet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonelZimmet> findOne(Long id);

    /**
     * Delete the "id" personelZimmet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

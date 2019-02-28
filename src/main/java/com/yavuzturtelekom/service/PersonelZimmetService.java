package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PersonelZimmet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PersonelZimmet> findAll(Pageable pageable);


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

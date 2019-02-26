package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PersonelOdeme;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PersonelOdeme.
 */
public interface PersonelOdemeService {

    /**
     * Save a personelOdeme.
     *
     * @param personelOdeme the entity to save
     * @return the persisted entity
     */
    PersonelOdeme save(PersonelOdeme personelOdeme);

    /**
     * Get all the personelOdemes.
     *
     * @return the list of entities
     */
    List<PersonelOdeme> findAll();


    /**
     * Get the "id" personelOdeme.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonelOdeme> findOne(Long id);

    /**
     * Delete the "id" personelOdeme.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

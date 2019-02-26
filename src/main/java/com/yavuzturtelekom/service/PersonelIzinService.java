package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.PersonelIzin;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PersonelIzin.
 */
public interface PersonelIzinService {

    /**
     * Save a personelIzin.
     *
     * @param personelIzin the entity to save
     * @return the persisted entity
     */
    PersonelIzin save(PersonelIzin personelIzin);

    /**
     * Get all the personelIzins.
     *
     * @return the list of entities
     */
    List<PersonelIzin> findAll();


    /**
     * Get the "id" personelIzin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PersonelIzin> findOne(Long id);

    /**
     * Delete the "id" personelIzin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

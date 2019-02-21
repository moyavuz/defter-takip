package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.ZimmetTuru;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ZimmetTuru.
 */
public interface ZimmetTuruService {

    /**
     * Save a zimmetTuru.
     *
     * @param zimmetTuru the entity to save
     * @return the persisted entity
     */
    ZimmetTuru save(ZimmetTuru zimmetTuru);

    /**
     * Get all the zimmetTurus.
     *
     * @return the list of entities
     */
    List<ZimmetTuru> findAll();


    /**
     * Get the "id" zimmetTuru.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ZimmetTuru> findOne(Long id);

    /**
     * Delete the "id" zimmetTuru.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

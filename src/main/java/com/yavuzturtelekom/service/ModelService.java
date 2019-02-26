package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Model;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Model.
 */
public interface ModelService {

    /**
     * Save a model.
     *
     * @param model the entity to save
     * @return the persisted entity
     */
    Model save(Model model);

    /**
     * Get all the models.
     *
     * @return the list of entities
     */
    List<Model> findAll();


    /**
     * Get the "id" model.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Model> findOne(Long id);

    /**
     * Delete the "id" model.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

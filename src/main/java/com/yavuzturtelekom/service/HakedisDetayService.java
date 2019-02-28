package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.HakedisDetay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HakedisDetay.
 */
public interface HakedisDetayService {

    /**
     * Save a hakedisDetay.
     *
     * @param hakedisDetay the entity to save
     * @return the persisted entity
     */
    HakedisDetay save(HakedisDetay hakedisDetay);

    /**
     * Get all the hakedisDetays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HakedisDetay> findAll(Pageable pageable);


    /**
     * Get the "id" hakedisDetay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HakedisDetay> findOne(Long id);

    /**
     * Delete the "id" hakedisDetay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

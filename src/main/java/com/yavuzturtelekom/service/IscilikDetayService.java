package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.IscilikDetay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing IscilikDetay.
 */
public interface IscilikDetayService {

    /**
     * Save a iscilikDetay.
     *
     * @param iscilikDetay the entity to save
     * @return the persisted entity
     */
    IscilikDetay save(IscilikDetay iscilikDetay);

    /**
     * Get all the iscilikDetays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IscilikDetay> findAll(Pageable pageable);


    /**
     * Get the "id" iscilikDetay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IscilikDetay> findOne(Long id);

    /**
     * Delete the "id" iscilikDetay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

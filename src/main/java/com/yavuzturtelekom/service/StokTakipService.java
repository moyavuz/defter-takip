package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.StokTakip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StokTakip.
 */
public interface StokTakipService {

    /**
     * Save a stokTakip.
     *
     * @param stokTakip the entity to save
     * @return the persisted entity
     */
    StokTakip save(StokTakip stokTakip);

    /**
     * Get all the stokTakips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StokTakip> findAll(Pageable pageable);


    /**
     * Get the "id" stokTakip.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StokTakip> findOne(Long id);

    /**
     * Delete the "id" stokTakip.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

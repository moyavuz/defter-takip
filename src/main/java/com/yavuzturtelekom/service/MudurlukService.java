package com.yavuzturtelekom.service;

import com.yavuzturtelekom.domain.Mudurluk;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Mudurluk.
 */
public interface MudurlukService {

    /**
     * Save a mudurluk.
     *
     * @param mudurluk the entity to save
     * @return the persisted entity
     */
    Mudurluk save(Mudurluk mudurluk);

    /**
     * Get all the mudurluks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Mudurluk> findAll(Pageable pageable);


    /**
     * Get the "id" mudurluk.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Mudurluk> findOne(Long id);

    /**
     * Delete the "id" mudurluk.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

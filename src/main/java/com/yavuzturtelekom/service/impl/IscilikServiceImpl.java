package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.IscilikService;
import com.yavuzturtelekom.domain.Iscilik;
import com.yavuzturtelekom.repository.IscilikRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Iscilik.
 */
@Service
@Transactional
public class IscilikServiceImpl implements IscilikService {

    private final Logger log = LoggerFactory.getLogger(IscilikServiceImpl.class);

    private final IscilikRepository iscilikRepository;

    public IscilikServiceImpl(IscilikRepository iscilikRepository) {
        this.iscilikRepository = iscilikRepository;
    }

    /**
     * Save a iscilik.
     *
     * @param iscilik the entity to save
     * @return the persisted entity
     */
    @Override
    public Iscilik save(Iscilik iscilik) {
        log.debug("Request to save Iscilik : {}", iscilik);
        return iscilikRepository.save(iscilik);
    }

    /**
     * Get all the isciliks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Iscilik> findAll(Pageable pageable) {
        log.debug("Request to get all Isciliks");
        return iscilikRepository.findAll(pageable);
    }


    /**
     * Get one iscilik by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Iscilik> findOne(Long id) {
        log.debug("Request to get Iscilik : {}", id);
        return iscilikRepository.findById(id);
    }

    /**
     * Delete the iscilik by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Iscilik : {}", id);        iscilikRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.DepartmanService;
import com.yavuzturtelekom.domain.Departman;
import com.yavuzturtelekom.repository.DepartmanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Departman.
 */
@Service
@Transactional
public class DepartmanServiceImpl implements DepartmanService {

    private final Logger log = LoggerFactory.getLogger(DepartmanServiceImpl.class);

    private final DepartmanRepository departmanRepository;

    public DepartmanServiceImpl(DepartmanRepository departmanRepository) {
        this.departmanRepository = departmanRepository;
    }

    /**
     * Save a departman.
     *
     * @param departman the entity to save
     * @return the persisted entity
     */
    @Override
    public Departman save(Departman departman) {
        log.debug("Request to save Departman : {}", departman);
        return departmanRepository.save(departman);
    }

    /**
     * Get all the departmen.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Departman> findAll() {
        log.debug("Request to get all Departmen");
        return departmanRepository.findAll();
    }


    /**
     * Get one departman by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Departman> findOne(Long id) {
        log.debug("Request to get Departman : {}", id);
        return departmanRepository.findById(id);
    }

    /**
     * Delete the departman by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Departman : {}", id);        departmanRepository.deleteById(id);
    }
}

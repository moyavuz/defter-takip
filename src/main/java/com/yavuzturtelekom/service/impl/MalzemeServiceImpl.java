package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.MalzemeService;
import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.repository.MalzemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Malzeme.
 */
@Service
@Transactional
public class MalzemeServiceImpl implements MalzemeService {

    private final Logger log = LoggerFactory.getLogger(MalzemeServiceImpl.class);

    private final MalzemeRepository malzemeRepository;

    public MalzemeServiceImpl(MalzemeRepository malzemeRepository) {
        this.malzemeRepository = malzemeRepository;
    }

    /**
     * Save a malzeme.
     *
     * @param malzeme the entity to save
     * @return the persisted entity
     */
    @Override
    public Malzeme save(Malzeme malzeme) {
        log.debug("Request to save Malzeme : {}", malzeme);
        return malzemeRepository.save(malzeme);
    }

    /**
     * Get all the malzemes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Malzeme> findAll(Pageable pageable) {
        log.debug("Request to get all Malzemes");
        return malzemeRepository.findAll(pageable);
    }



    /**
     *  get all the malzemes where Poz is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Malzeme> findAllWherePozIsNull() {
        log.debug("Request to get all malzemes where Poz is null");
        return StreamSupport
            .stream(malzemeRepository.findAll().spliterator(), false)
            .filter(malzeme -> malzeme.getPoz() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one malzeme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Malzeme> findOne(Long id) {
        log.debug("Request to get Malzeme : {}", id);
        return malzemeRepository.findById(id);
    }

    /**
     * Delete the malzeme by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Malzeme : {}", id);        malzemeRepository.deleteById(id);
    }
}

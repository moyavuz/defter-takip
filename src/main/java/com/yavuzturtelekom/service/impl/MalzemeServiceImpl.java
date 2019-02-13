package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.MalzemeService;
import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.repository.MalzemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Malzeme> findAll() {
        log.debug("Request to get all Malzemes");
        return malzemeRepository.findAll();
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

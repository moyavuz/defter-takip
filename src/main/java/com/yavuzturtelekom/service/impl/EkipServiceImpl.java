package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.EkipService;
import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.repository.EkipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ekip.
 */
@Service
@Transactional
public class EkipServiceImpl implements EkipService {

    private final Logger log = LoggerFactory.getLogger(EkipServiceImpl.class);

    private final EkipRepository ekipRepository;

    public EkipServiceImpl(EkipRepository ekipRepository) {
        this.ekipRepository = ekipRepository;
    }

    /**
     * Save a ekip.
     *
     * @param ekip the entity to save
     * @return the persisted entity
     */
    @Override
    public Ekip save(Ekip ekip) {
        log.debug("Request to save Ekip : {}", ekip);
        return ekipRepository.save(ekip);
    }

    /**
     * Get all the ekips.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Ekip> findAll(Pageable pageable) {
        log.debug("Request to get all Ekips");
        return ekipRepository.findAll(pageable);
    }

    /**
     * Get all the Ekip with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Ekip> findAllWithEagerRelationships(Pageable pageable) {
        return ekipRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one ekip by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Ekip> findOne(Long id) {
        log.debug("Request to get Ekip : {}", id);
        return ekipRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the ekip by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ekip : {}", id);
        ekipRepository.deleteById(id);
    }
}

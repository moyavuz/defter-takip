package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.CalisanService;
import com.yavuzturtelekom.domain.Calisan;
import com.yavuzturtelekom.repository.CalisanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Calisan.
 */
@Service
@Transactional
public class CalisanServiceImpl implements CalisanService {

    private final Logger log = LoggerFactory.getLogger(CalisanServiceImpl.class);

    private final CalisanRepository calisanRepository;

    public CalisanServiceImpl(CalisanRepository calisanRepository) {
        this.calisanRepository = calisanRepository;
    }

    /**
     * Save a calisan.
     *
     * @param calisan the entity to save
     * @return the persisted entity
     */
    @Override
    public Calisan save(Calisan calisan) {
        log.debug("Request to save Calisan : {}", calisan);
        return calisanRepository.save(calisan);
    }

    /**
     * Get all the calisans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Calisan> findAll(Pageable pageable) {
        log.debug("Request to get all Calisans");
        return calisanRepository.findAll(pageable);
    }


    /**
     * Get one calisan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Calisan> findOne(Long id) {
        log.debug("Request to get Calisan : {}", id);
        return calisanRepository.findById(id);
    }

    /**
     * Delete the calisan by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calisan : {}", id);        calisanRepository.deleteById(id);
    }
}

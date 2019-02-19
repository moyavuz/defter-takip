package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.IscilikDetayService;
import com.yavuzturtelekom.domain.IscilikDetay;
import com.yavuzturtelekom.repository.IscilikDetayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing IscilikDetay.
 */
@Service
@Transactional
public class IscilikDetayServiceImpl implements IscilikDetayService {

    private final Logger log = LoggerFactory.getLogger(IscilikDetayServiceImpl.class);

    private final IscilikDetayRepository iscilikDetayRepository;

    public IscilikDetayServiceImpl(IscilikDetayRepository iscilikDetayRepository) {
        this.iscilikDetayRepository = iscilikDetayRepository;
    }

    /**
     * Save a iscilikDetay.
     *
     * @param iscilikDetay the entity to save
     * @return the persisted entity
     */
    @Override
    public IscilikDetay save(IscilikDetay iscilikDetay) {
        log.debug("Request to save IscilikDetay : {}", iscilikDetay);
        return iscilikDetayRepository.save(iscilikDetay);
    }

    /**
     * Get all the iscilikDetays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IscilikDetay> findAll(Pageable pageable) {
        log.debug("Request to get all IscilikDetays");
        return iscilikDetayRepository.findAll(pageable);
    }


    /**
     * Get one iscilikDetay by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IscilikDetay> findOne(Long id) {
        log.debug("Request to get IscilikDetay : {}", id);
        return iscilikDetayRepository.findById(id);
    }

    /**
     * Delete the iscilikDetay by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IscilikDetay : {}", id);        iscilikDetayRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service.impl;

import com.yavuzturtelekom.service.MudurlukService;
import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.repository.MudurlukRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Mudurluk.
 */
@Service
@Transactional
public class MudurlukServiceImpl implements MudurlukService {

    private final Logger log = LoggerFactory.getLogger(MudurlukServiceImpl.class);

    private final MudurlukRepository mudurlukRepository;

    public MudurlukServiceImpl(MudurlukRepository mudurlukRepository) {
        this.mudurlukRepository = mudurlukRepository;
    }

    /**
     * Save a mudurluk.
     *
     * @param mudurluk the entity to save
     * @return the persisted entity
     */
    @Override
    public Mudurluk save(Mudurluk mudurluk) {
        log.debug("Request to save Mudurluk : {}", mudurluk);
        return mudurlukRepository.save(mudurluk);
    }

    /**
     * Get all the mudurluks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Mudurluk> findAll() {
        log.debug("Request to get all Mudurluks");
        return mudurlukRepository.findAll();
    }


    /**
     * Get one mudurluk by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Mudurluk> findOne(Long id) {
        log.debug("Request to get Mudurluk : {}", id);
        return mudurlukRepository.findById(id);
    }

    /**
     * Delete the mudurluk by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mudurluk : {}", id);        mudurlukRepository.deleteById(id);
    }
}

package com.yavuzturtelekom.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.yavuzturtelekom.domain.EskalasyonTuru;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.EskalasyonTuruRepository;
import com.yavuzturtelekom.service.dto.EskalasyonTuruCriteria;

/**
 * Service for executing complex queries for EskalasyonTuru entities in the database.
 * The main input is a {@link EskalasyonTuruCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EskalasyonTuru} or a {@link Page} of {@link EskalasyonTuru} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EskalasyonTuruQueryService extends QueryService<EskalasyonTuru> {

    private final Logger log = LoggerFactory.getLogger(EskalasyonTuruQueryService.class);

    private final EskalasyonTuruRepository eskalasyonTuruRepository;

    public EskalasyonTuruQueryService(EskalasyonTuruRepository eskalasyonTuruRepository) {
        this.eskalasyonTuruRepository = eskalasyonTuruRepository;
    }

    /**
     * Return a {@link List} of {@link EskalasyonTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EskalasyonTuru> findByCriteria(EskalasyonTuruCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EskalasyonTuru> specification = createSpecification(criteria);
        return eskalasyonTuruRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EskalasyonTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EskalasyonTuru> findByCriteria(EskalasyonTuruCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EskalasyonTuru> specification = createSpecification(criteria);
        return eskalasyonTuruRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EskalasyonTuruCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EskalasyonTuru> specification = createSpecification(criteria);
        return eskalasyonTuruRepository.count(specification);
    }

    /**
     * Function to convert EskalasyonTuruCriteria to a {@link Specification}
     */
    private Specification<EskalasyonTuru> createSpecification(EskalasyonTuruCriteria criteria) {
        Specification<EskalasyonTuru> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EskalasyonTuru_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), EskalasyonTuru_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), EskalasyonTuru_.aciklama));
            }
            if (criteria.getKisaltma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKisaltma(), EskalasyonTuru_.kisaltma));
            }
        }
        return specification;
    }
}

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

import com.yavuzturtelekom.domain.Eskalasyon;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.EskalasyonRepository;
import com.yavuzturtelekom.service.dto.EskalasyonCriteria;

/**
 * Service for executing complex queries for Eskalasyon entities in the database.
 * The main input is a {@link EskalasyonCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Eskalasyon} or a {@link Page} of {@link Eskalasyon} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EskalasyonQueryService extends QueryService<Eskalasyon> {

    private final Logger log = LoggerFactory.getLogger(EskalasyonQueryService.class);

    private final EskalasyonRepository eskalasyonRepository;

    public EskalasyonQueryService(EskalasyonRepository eskalasyonRepository) {
        this.eskalasyonRepository = eskalasyonRepository;
    }

    /**
     * Return a {@link List} of {@link Eskalasyon} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Eskalasyon> findByCriteria(EskalasyonCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Eskalasyon> specification = createSpecification(criteria);
        return eskalasyonRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Eskalasyon} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Eskalasyon> findByCriteria(EskalasyonCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Eskalasyon> specification = createSpecification(criteria);
        return eskalasyonRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EskalasyonCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Eskalasyon> specification = createSpecification(criteria);
        return eskalasyonRepository.count(specification);
    }

    /**
     * Function to convert EskalasyonCriteria to a {@link Specification}
     */
    private Specification<Eskalasyon> createSpecification(EskalasyonCriteria criteria) {
        Specification<Eskalasyon> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Eskalasyon_.id));
            }
            if (criteria.getDeger() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeger(), Eskalasyon_.deger));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), Eskalasyon_.tarih));
            }
            if (criteria.getTuruId() != null) {
                specification = specification.and(buildSpecification(criteria.getTuruId(),
                    root -> root.join(Eskalasyon_.turu, JoinType.LEFT).get(EskalasyonTuru_.id)));
            }
        }
        return specification;
    }
}

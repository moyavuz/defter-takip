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

import com.yavuzturtelekom.domain.DovizKur;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.DovizKurRepository;
import com.yavuzturtelekom.service.dto.DovizKurCriteria;

/**
 * Service for executing complex queries for DovizKur entities in the database.
 * The main input is a {@link DovizKurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DovizKur} or a {@link Page} of {@link DovizKur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DovizKurQueryService extends QueryService<DovizKur> {

    private final Logger log = LoggerFactory.getLogger(DovizKurQueryService.class);

    private final DovizKurRepository dovizKurRepository;

    public DovizKurQueryService(DovizKurRepository dovizKurRepository) {
        this.dovizKurRepository = dovizKurRepository;
    }

    /**
     * Return a {@link List} of {@link DovizKur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DovizKur> findByCriteria(DovizKurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DovizKur> specification = createSpecification(criteria);
        return dovizKurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DovizKur} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DovizKur> findByCriteria(DovizKurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DovizKur> specification = createSpecification(criteria);
        return dovizKurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DovizKurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DovizKur> specification = createSpecification(criteria);
        return dovizKurRepository.count(specification);
    }

    /**
     * Function to convert DovizKurCriteria to a {@link Specification}
     */
    private Specification<DovizKur> createSpecification(DovizKurCriteria criteria) {
        Specification<DovizKur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DovizKur_.id));
            }
            if (criteria.getParaBirimi() != null) {
                specification = specification.and(buildSpecification(criteria.getParaBirimi(), DovizKur_.paraBirimi));
            }
            if (criteria.getDeger() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeger(), DovizKur_.deger));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), DovizKur_.tarih));
            }
        }
        return specification;
    }
}

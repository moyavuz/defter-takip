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

import com.yavuzturtelekom.domain.Marka;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.MarkaRepository;
import com.yavuzturtelekom.service.dto.MarkaCriteria;

/**
 * Service for executing complex queries for Marka entities in the database.
 * The main input is a {@link MarkaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Marka} or a {@link Page} of {@link Marka} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MarkaQueryService extends QueryService<Marka> {

    private final Logger log = LoggerFactory.getLogger(MarkaQueryService.class);

    private final MarkaRepository markaRepository;

    public MarkaQueryService(MarkaRepository markaRepository) {
        this.markaRepository = markaRepository;
    }

    /**
     * Return a {@link List} of {@link Marka} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Marka> findByCriteria(MarkaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Marka> specification = createSpecification(criteria);
        return markaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Marka} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Marka> findByCriteria(MarkaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Marka> specification = createSpecification(criteria);
        return markaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MarkaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Marka> specification = createSpecification(criteria);
        return markaRepository.count(specification);
    }

    /**
     * Function to convert MarkaCriteria to a {@link Specification}
     */
    private Specification<Marka> createSpecification(MarkaCriteria criteria) {
        Specification<Marka> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Marka_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Marka_.ad));
            }
        }
        return specification;
    }
}

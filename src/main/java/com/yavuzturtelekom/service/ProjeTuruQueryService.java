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

import com.yavuzturtelekom.domain.ProjeTuru;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.ProjeTuruRepository;
import com.yavuzturtelekom.service.dto.ProjeTuruCriteria;

/**
 * Service for executing complex queries for ProjeTuru entities in the database.
 * The main input is a {@link ProjeTuruCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjeTuru} or a {@link Page} of {@link ProjeTuru} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjeTuruQueryService extends QueryService<ProjeTuru> {

    private final Logger log = LoggerFactory.getLogger(ProjeTuruQueryService.class);

    private final ProjeTuruRepository projeTuruRepository;

    public ProjeTuruQueryService(ProjeTuruRepository projeTuruRepository) {
        this.projeTuruRepository = projeTuruRepository;
    }

    /**
     * Return a {@link List} of {@link ProjeTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjeTuru> findByCriteria(ProjeTuruCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjeTuru> specification = createSpecification(criteria);
        return projeTuruRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjeTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjeTuru> findByCriteria(ProjeTuruCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjeTuru> specification = createSpecification(criteria);
        return projeTuruRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjeTuruCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjeTuru> specification = createSpecification(criteria);
        return projeTuruRepository.count(specification);
    }

    /**
     * Function to convert ProjeTuruCriteria to a {@link Specification}
     */
    private Specification<ProjeTuru> createSpecification(ProjeTuruCriteria criteria) {
        Specification<ProjeTuru> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjeTuru_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), ProjeTuru_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), ProjeTuru_.aciklama));
            }
            if (criteria.getKisaltma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKisaltma(), ProjeTuru_.kisaltma));
            }
        }
        return specification;
    }
}

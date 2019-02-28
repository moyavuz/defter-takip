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

import com.yavuzturtelekom.domain.HakedisTuru;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.HakedisTuruRepository;
import com.yavuzturtelekom.service.dto.HakedisTuruCriteria;

/**
 * Service for executing complex queries for HakedisTuru entities in the database.
 * The main input is a {@link HakedisTuruCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HakedisTuru} or a {@link Page} of {@link HakedisTuru} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HakedisTuruQueryService extends QueryService<HakedisTuru> {

    private final Logger log = LoggerFactory.getLogger(HakedisTuruQueryService.class);

    private final HakedisTuruRepository hakedisTuruRepository;

    public HakedisTuruQueryService(HakedisTuruRepository hakedisTuruRepository) {
        this.hakedisTuruRepository = hakedisTuruRepository;
    }

    /**
     * Return a {@link List} of {@link HakedisTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HakedisTuru> findByCriteria(HakedisTuruCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HakedisTuru> specification = createSpecification(criteria);
        return hakedisTuruRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HakedisTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HakedisTuru> findByCriteria(HakedisTuruCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HakedisTuru> specification = createSpecification(criteria);
        return hakedisTuruRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HakedisTuruCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HakedisTuru> specification = createSpecification(criteria);
        return hakedisTuruRepository.count(specification);
    }

    /**
     * Function to convert HakedisTuruCriteria to a {@link Specification}
     */
    private Specification<HakedisTuru> createSpecification(HakedisTuruCriteria criteria) {
        Specification<HakedisTuru> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HakedisTuru_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), HakedisTuru_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), HakedisTuru_.aciklama));
            }
        }
        return specification;
    }
}

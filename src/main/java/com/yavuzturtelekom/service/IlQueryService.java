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

import com.yavuzturtelekom.domain.Il;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.IlRepository;
import com.yavuzturtelekom.service.dto.IlCriteria;

/**
 * Service for executing complex queries for Il entities in the database.
 * The main input is a {@link IlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Il} or a {@link Page} of {@link Il} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IlQueryService extends QueryService<Il> {

    private final Logger log = LoggerFactory.getLogger(IlQueryService.class);

    private final IlRepository ilRepository;

    public IlQueryService(IlRepository ilRepository) {
        this.ilRepository = ilRepository;
    }

    /**
     * Return a {@link List} of {@link Il} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Il> findByCriteria(IlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Il> specification = createSpecification(criteria);
        return ilRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Il} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Il> findByCriteria(IlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Il> specification = createSpecification(criteria);
        return ilRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Il> specification = createSpecification(criteria);
        return ilRepository.count(specification);
    }

    /**
     * Function to convert IlCriteria to a {@link Specification}
     */
    private Specification<Il> createSpecification(IlCriteria criteria) {
        Specification<Il> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Il_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Il_.ad));
            }
        }
        return specification;
    }
}

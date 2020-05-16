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

import com.yavuzturtelekom.domain.Unvan;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.UnvanRepository;
import com.yavuzturtelekom.service.dto.UnvanCriteria;

/**
 * Service for executing complex queries for Unvan entities in the database.
 * The main input is a {@link UnvanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Unvan} or a {@link Page} of {@link Unvan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UnvanQueryService extends QueryService<Unvan> {

    private final Logger log = LoggerFactory.getLogger(UnvanQueryService.class);

    private final UnvanRepository unvanRepository;

    public UnvanQueryService(UnvanRepository unvanRepository) {
        this.unvanRepository = unvanRepository;
    }

    /**
     * Return a {@link List} of {@link Unvan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Unvan> findByCriteria(UnvanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Unvan> specification = createSpecification(criteria);
        return unvanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Unvan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Unvan> findByCriteria(UnvanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Unvan> specification = createSpecification(criteria);
        return unvanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UnvanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Unvan> specification = createSpecification(criteria);
        return unvanRepository.count(specification);
    }

    /**
     * Function to convert UnvanCriteria to a {@link Specification}
     */
    private Specification<Unvan> createSpecification(UnvanCriteria criteria) {
        Specification<Unvan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Unvan_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Unvan_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Unvan_.aciklama));
            }
        }
        return specification;
    }
}

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

import com.yavuzturtelekom.domain.Santral;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.SantralRepository;
import com.yavuzturtelekom.service.dto.SantralCriteria;

/**
 * Service for executing complex queries for Santral entities in the database.
 * The main input is a {@link SantralCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Santral} or a {@link Page} of {@link Santral} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SantralQueryService extends QueryService<Santral> {

    private final Logger log = LoggerFactory.getLogger(SantralQueryService.class);

    private final SantralRepository santralRepository;

    public SantralQueryService(SantralRepository santralRepository) {
        this.santralRepository = santralRepository;
    }

    /**
     * Return a {@link List} of {@link Santral} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Santral> findByCriteria(SantralCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Santral> specification = createSpecification(criteria);
        return santralRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Santral} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Santral> findByCriteria(SantralCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Santral> specification = createSpecification(criteria);
        return santralRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SantralCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Santral> specification = createSpecification(criteria);
        return santralRepository.count(specification);
    }

    /**
     * Function to convert SantralCriteria to a {@link Specification}
     */
    private Specification<Santral> createSpecification(SantralCriteria criteria) {
        Specification<Santral> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Santral_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Santral_.ad));
            }
            if (criteria.getMudurlukId() != null) {
                specification = specification.and(buildSpecification(criteria.getMudurlukId(),
                    root -> root.join(Santral_.mudurluk, JoinType.LEFT).get(Mudurluk_.id)));
            }
            if (criteria.getSantralSorumluId() != null) {
                specification = specification.and(buildSpecification(criteria.getSantralSorumluId(),
                    root -> root.join(Santral_.santralSorumlu, JoinType.LEFT).get(Personel_.id)));
            }
        }
        return specification;
    }
}

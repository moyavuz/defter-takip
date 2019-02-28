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

import com.yavuzturtelekom.domain.HakedisDetay;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.HakedisDetayRepository;
import com.yavuzturtelekom.service.dto.HakedisDetayCriteria;

/**
 * Service for executing complex queries for HakedisDetay entities in the database.
 * The main input is a {@link HakedisDetayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HakedisDetay} or a {@link Page} of {@link HakedisDetay} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HakedisDetayQueryService extends QueryService<HakedisDetay> {

    private final Logger log = LoggerFactory.getLogger(HakedisDetayQueryService.class);

    private final HakedisDetayRepository hakedisDetayRepository;

    public HakedisDetayQueryService(HakedisDetayRepository hakedisDetayRepository) {
        this.hakedisDetayRepository = hakedisDetayRepository;
    }

    /**
     * Return a {@link List} of {@link HakedisDetay} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HakedisDetay> findByCriteria(HakedisDetayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HakedisDetay> specification = createSpecification(criteria);
        return hakedisDetayRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HakedisDetay} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HakedisDetay> findByCriteria(HakedisDetayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HakedisDetay> specification = createSpecification(criteria);
        return hakedisDetayRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HakedisDetayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HakedisDetay> specification = createSpecification(criteria);
        return hakedisDetayRepository.count(specification);
    }

    /**
     * Function to convert HakedisDetayCriteria to a {@link Specification}
     */
    private Specification<HakedisDetay> createSpecification(HakedisDetayCriteria criteria) {
        Specification<HakedisDetay> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), HakedisDetay_.id));
            }
            if (criteria.getMiktar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMiktar(), HakedisDetay_.miktar));
            }
            if (criteria.getHakedisId() != null) {
                specification = specification.and(buildSpecification(criteria.getHakedisId(),
                    root -> root.join(HakedisDetay_.hakedis, JoinType.LEFT).get(Hakedis_.id)));
            }
            if (criteria.getPozId() != null) {
                specification = specification.and(buildSpecification(criteria.getPozId(),
                    root -> root.join(HakedisDetay_.poz, JoinType.LEFT).get(Poz_.id)));
            }
        }
        return specification;
    }
}

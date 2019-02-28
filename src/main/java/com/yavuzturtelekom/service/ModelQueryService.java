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

import com.yavuzturtelekom.domain.Model;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.ModelRepository;
import com.yavuzturtelekom.service.dto.ModelCriteria;

/**
 * Service for executing complex queries for Model entities in the database.
 * The main input is a {@link ModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Model} or a {@link Page} of {@link Model} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModelQueryService extends QueryService<Model> {

    private final Logger log = LoggerFactory.getLogger(ModelQueryService.class);

    private final ModelRepository modelRepository;

    public ModelQueryService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    /**
     * Return a {@link List} of {@link Model} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Model> findByCriteria(ModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Model> specification = createSpecification(criteria);
        return modelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Model} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Model> findByCriteria(ModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Model> specification = createSpecification(criteria);
        return modelRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Model> specification = createSpecification(criteria);
        return modelRepository.count(specification);
    }

    /**
     * Function to convert ModelCriteria to a {@link Specification}
     */
    private Specification<Model> createSpecification(ModelCriteria criteria) {
        Specification<Model> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Model_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Model_.ad));
            }
            if (criteria.getMarkaId() != null) {
                specification = specification.and(buildSpecification(criteria.getMarkaId(),
                    root -> root.join(Model_.marka, JoinType.LEFT).get(Marka_.id)));
            }
        }
        return specification;
    }
}

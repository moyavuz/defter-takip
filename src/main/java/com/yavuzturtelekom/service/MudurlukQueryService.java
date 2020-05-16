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

import com.yavuzturtelekom.domain.Mudurluk;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.MudurlukRepository;
import com.yavuzturtelekom.service.dto.MudurlukCriteria;

/**
 * Service for executing complex queries for Mudurluk entities in the database.
 * The main input is a {@link MudurlukCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Mudurluk} or a {@link Page} of {@link Mudurluk} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MudurlukQueryService extends QueryService<Mudurluk> {

    private final Logger log = LoggerFactory.getLogger(MudurlukQueryService.class);

    private final MudurlukRepository mudurlukRepository;

    public MudurlukQueryService(MudurlukRepository mudurlukRepository) {
        this.mudurlukRepository = mudurlukRepository;
    }

    /**
     * Return a {@link List} of {@link Mudurluk} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Mudurluk> findByCriteria(MudurlukCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Mudurluk> specification = createSpecification(criteria);
        return mudurlukRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Mudurluk} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Mudurluk> findByCriteria(MudurlukCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Mudurluk> specification = createSpecification(criteria);
        return mudurlukRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MudurlukCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Mudurluk> specification = createSpecification(criteria);
        return mudurlukRepository.count(specification);
    }

    /**
     * Function to convert MudurlukCriteria to a {@link Specification}
     */
    private Specification<Mudurluk> createSpecification(MudurlukCriteria criteria) {
        Specification<Mudurluk> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Mudurluk_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Mudurluk_.ad));
            }
            if (criteria.getAdres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdres(), Mudurluk_.adres));
            }
            if (criteria.getMudurlukSorumluId() != null) {
                specification = specification.and(buildSpecification(criteria.getMudurlukSorumluId(),
                    root -> root.join(Mudurluk_.mudurlukSorumlu, JoinType.LEFT).get(Personel_.id)));
            }
            if (criteria.getIlId() != null) {
                specification = specification.and(buildSpecification(criteria.getIlId(),
                    root -> root.join(Mudurluk_.il, JoinType.LEFT).get(Il_.id)));
            }
        }
        return specification;
    }
}

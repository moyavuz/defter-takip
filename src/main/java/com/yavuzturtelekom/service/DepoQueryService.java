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

import com.yavuzturtelekom.domain.Depo;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.DepoRepository;
import com.yavuzturtelekom.service.dto.DepoCriteria;

/**
 * Service for executing complex queries for Depo entities in the database.
 * The main input is a {@link DepoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Depo} or a {@link Page} of {@link Depo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepoQueryService extends QueryService<Depo> {

    private final Logger log = LoggerFactory.getLogger(DepoQueryService.class);

    private final DepoRepository depoRepository;

    public DepoQueryService(DepoRepository depoRepository) {
        this.depoRepository = depoRepository;
    }

    /**
     * Return a {@link List} of {@link Depo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Depo> findByCriteria(DepoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Depo> specification = createSpecification(criteria);
        return depoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Depo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Depo> findByCriteria(DepoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Depo> specification = createSpecification(criteria);
        return depoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Depo> specification = createSpecification(criteria);
        return depoRepository.count(specification);
    }

    /**
     * Function to convert DepoCriteria to a {@link Specification}
     */
    private Specification<Depo> createSpecification(DepoCriteria criteria) {
        Specification<Depo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Depo_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Depo_.ad));
            }
            if (criteria.getAdres() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdres(), Depo_.adres));
            }
            if (criteria.getTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getTuru(), Depo_.turu));
            }
            if (criteria.getStokTakipId() != null) {
                specification = specification.and(buildSpecification(criteria.getStokTakipId(),
                    root -> root.join(Depo_.stokTakips, JoinType.LEFT).get(StokTakip_.id)));
            }
            if (criteria.getSorumluId() != null) {
                specification = specification.and(buildSpecification(criteria.getSorumluId(),
                    root -> root.join(Depo_.sorumlu, JoinType.LEFT).get(Personel_.id)));
            }
        }
        return specification;
    }
}

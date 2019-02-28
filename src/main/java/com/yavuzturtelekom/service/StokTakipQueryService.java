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

import com.yavuzturtelekom.domain.StokTakip;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.StokTakipRepository;
import com.yavuzturtelekom.service.dto.StokTakipCriteria;

/**
 * Service for executing complex queries for StokTakip entities in the database.
 * The main input is a {@link StokTakipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StokTakip} or a {@link Page} of {@link StokTakip} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StokTakipQueryService extends QueryService<StokTakip> {

    private final Logger log = LoggerFactory.getLogger(StokTakipQueryService.class);

    private final StokTakipRepository stokTakipRepository;

    public StokTakipQueryService(StokTakipRepository stokTakipRepository) {
        this.stokTakipRepository = stokTakipRepository;
    }

    /**
     * Return a {@link List} of {@link StokTakip} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StokTakip> findByCriteria(StokTakipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StokTakip> specification = createSpecification(criteria);
        return stokTakipRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StokTakip} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StokTakip> findByCriteria(StokTakipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StokTakip> specification = createSpecification(criteria);
        return stokTakipRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StokTakipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StokTakip> specification = createSpecification(criteria);
        return stokTakipRepository.count(specification);
    }

    /**
     * Function to convert StokTakipCriteria to a {@link Specification}
     */
    private Specification<StokTakip> createSpecification(StokTakipCriteria criteria) {
        Specification<StokTakip> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StokTakip_.id));
            }
            if (criteria.getMiktar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMiktar(), StokTakip_.miktar));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), StokTakip_.tarih));
            }
            if (criteria.getHareketTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getHareketTuru(), StokTakip_.hareketTuru));
            }
            if (criteria.getEkipId() != null) {
                specification = specification.and(buildSpecification(criteria.getEkipId(),
                    root -> root.join(StokTakip_.ekip, JoinType.LEFT).get(Ekip_.id)));
            }
            if (criteria.getMalzemeId() != null) {
                specification = specification.and(buildSpecification(criteria.getMalzemeId(),
                    root -> root.join(StokTakip_.malzeme, JoinType.LEFT).get(Malzeme_.id)));
            }
            if (criteria.getDepoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepoId(),
                    root -> root.join(StokTakip_.depo, JoinType.LEFT).get(Depo_.id)));
            }
        }
        return specification;
    }
}

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

import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.HakedisRepository;
import com.yavuzturtelekom.service.dto.HakedisCriteria;

/**
 * Service for executing complex queries for Hakedis entities in the database.
 * The main input is a {@link HakedisCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Hakedis} or a {@link Page} of {@link Hakedis} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HakedisQueryService extends QueryService<Hakedis> {

    private final Logger log = LoggerFactory.getLogger(HakedisQueryService.class);

    private final HakedisRepository hakedisRepository;

    public HakedisQueryService(HakedisRepository hakedisRepository) {
        this.hakedisRepository = hakedisRepository;
    }

    /**
     * Return a {@link List} of {@link Hakedis} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Hakedis> findByCriteria(HakedisCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hakedis> specification = createSpecification(criteria);
        return hakedisRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Hakedis} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Hakedis> findByCriteria(HakedisCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hakedis> specification = createSpecification(criteria);
        return hakedisRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HakedisCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hakedis> specification = createSpecification(criteria);
        return hakedisRepository.count(specification);
    }

    /**
     * Function to convert HakedisCriteria to a {@link Specification}
     */
    private Specification<Hakedis> createSpecification(HakedisCriteria criteria) {
        Specification<Hakedis> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Hakedis_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Hakedis_.ad));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), Hakedis_.tarih));
            }
            if (criteria.getSeriNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeriNo(), Hakedis_.seriNo));
            }
            if (criteria.getDefterNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefterNo(), Hakedis_.defterNo));
            }
            if (criteria.getCizimNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCizimNo(), Hakedis_.cizimNo));
            }
            if (criteria.getOnemDerecesi() != null) {
                specification = specification.and(buildSpecification(criteria.getOnemDerecesi(), Hakedis_.onemDerecesi));
            }
            if (criteria.getIsDurumu() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDurumu(), Hakedis_.isDurumu));
            }
            if (criteria.getOdemeDurumu() != null) {
                specification = specification.and(buildSpecification(criteria.getOdemeDurumu(), Hakedis_.odemeDurumu));
            }
            if (criteria.getOdemeNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOdemeNo(), Hakedis_.odemeNo));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Hakedis_.aciklama));
            }
            if (criteria.getHakedisDetayId() != null) {
                specification = specification.and(buildSpecification(criteria.getHakedisDetayId(),
                    root -> root.join(Hakedis_.hakedisDetays, JoinType.LEFT).get(HakedisDetay_.id)));
            }
            if (criteria.getSantralId() != null) {
                specification = specification.and(buildSpecification(criteria.getSantralId(),
                    root -> root.join(Hakedis_.santral, JoinType.LEFT).get(Santral_.id)));
            }
            if (criteria.getTuruId() != null) {
                specification = specification.and(buildSpecification(criteria.getTuruId(),
                    root -> root.join(Hakedis_.turu, JoinType.LEFT).get(HakedisTuru_.id)));
            }
            if (criteria.getEkipId() != null) {
                specification = specification.and(buildSpecification(criteria.getEkipId(),
                    root -> root.join(Hakedis_.ekip, JoinType.LEFT).get(Ekip_.id)));
            }
            if (criteria.getProjeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjeId(),
                    root -> root.join(Hakedis_.proje, JoinType.LEFT).get(Proje_.id)));
            }
        }
        return specification;
    }
}

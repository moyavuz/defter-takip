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

import com.yavuzturtelekom.domain.Arac;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.AracRepository;
import com.yavuzturtelekom.service.dto.AracCriteria;

/**
 * Service for executing complex queries for Arac entities in the database.
 * The main input is a {@link AracCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Arac} or a {@link Page} of {@link Arac} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AracQueryService extends QueryService<Arac> {

    private final Logger log = LoggerFactory.getLogger(AracQueryService.class);

    private final AracRepository aracRepository;

    public AracQueryService(AracRepository aracRepository) {
        this.aracRepository = aracRepository;
    }

    /**
     * Return a {@link List} of {@link Arac} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Arac> findByCriteria(AracCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Arac> specification = createSpecification(criteria);
        return aracRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Arac} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Arac> findByCriteria(AracCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Arac> specification = createSpecification(criteria);
        return aracRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AracCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Arac> specification = createSpecification(criteria);
        return aracRepository.count(specification);
    }

    /**
     * Function to convert AracCriteria to a {@link Specification}
     */
    private Specification<Arac> createSpecification(AracCriteria criteria) {
        Specification<Arac> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Arac_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Arac_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Arac_.aciklama));
            }
            if (criteria.getModelYili() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModelYili(), Arac_.modelYili));
            }
            if (criteria.getYakitTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getYakitTuru(), Arac_.yakitTuru));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), Arac_.tarih));
            }
            if (criteria.getMuayeneTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMuayeneTarih(), Arac_.muayeneTarih));
            }
            if (criteria.getKaskoTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKaskoTarih(), Arac_.kaskoTarih));
            }
            if (criteria.getSigortaTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSigortaTarih(), Arac_.sigortaTarih));
            }
            if (criteria.getBakimTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBakimTarih(), Arac_.bakimTarih));
            }
            if (criteria.getModelId() != null) {
                specification = specification.and(buildSpecification(criteria.getModelId(),
                    root -> root.join(Arac_.model, JoinType.LEFT).get(Model_.id)));
            }
        }
        return specification;
    }
}

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

import com.yavuzturtelekom.domain.Proje;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.ProjeRepository;
import com.yavuzturtelekom.service.dto.ProjeCriteria;

/**
 * Service for executing complex queries for Proje entities in the database.
 * The main input is a {@link ProjeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Proje} or a {@link Page} of {@link Proje} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjeQueryService extends QueryService<Proje> {

    private final Logger log = LoggerFactory.getLogger(ProjeQueryService.class);

    private final ProjeRepository projeRepository;

    public ProjeQueryService(ProjeRepository projeRepository) {
        this.projeRepository = projeRepository;
    }

    /**
     * Return a {@link List} of {@link Proje} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Proje> findByCriteria(ProjeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Proje> specification = createSpecification(criteria);
        return projeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Proje} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Proje> findByCriteria(ProjeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Proje> specification = createSpecification(criteria);
        return projeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Proje> specification = createSpecification(criteria);
        return projeRepository.count(specification);
    }

    /**
     * Function to convert ProjeCriteria to a {@link Specification}
     */
    private Specification<Proje> createSpecification(ProjeCriteria criteria) {
        Specification<Proje> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Proje_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Proje_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Proje_.aciklama));
            }
            if (criteria.getProtokolNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProtokolNo(), Proje_.protokolNo));
            }
            if (criteria.getDurumu() != null) {
                specification = specification.and(buildSpecification(criteria.getDurumu(), Proje_.durumu));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), Proje_.tarih));
            }
            if (criteria.getBaslamaTarihi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBaslamaTarihi(), Proje_.baslamaTarihi));
            }
            if (criteria.getBitisTarihi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBitisTarihi(), Proje_.bitisTarihi));
            }
            if (criteria.getHakedisId() != null) {
                specification = specification.and(buildSpecification(criteria.getHakedisId(),
                    root -> root.join(Proje_.hakedis, JoinType.LEFT).get(Hakedis_.id)));
            }
            if (criteria.getTuruId() != null) {
                specification = specification.and(buildSpecification(criteria.getTuruId(),
                    root -> root.join(Proje_.turu, JoinType.LEFT).get(ProjeTuru_.id)));
            }
            if (criteria.getMudurlukId() != null) {
                specification = specification.and(buildSpecification(criteria.getMudurlukId(),
                    root -> root.join(Proje_.mudurluk, JoinType.LEFT).get(Mudurluk_.id)));
            }
        }
        return specification;
    }
}

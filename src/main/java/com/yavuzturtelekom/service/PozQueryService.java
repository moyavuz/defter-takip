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

import com.yavuzturtelekom.domain.Poz;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PozRepository;
import com.yavuzturtelekom.service.dto.PozCriteria;

/**
 * Service for executing complex queries for Poz entities in the database.
 * The main input is a {@link PozCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Poz} or a {@link Page} of {@link Poz} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PozQueryService extends QueryService<Poz> {

    private final Logger log = LoggerFactory.getLogger(PozQueryService.class);

    private final PozRepository pozRepository;

    public PozQueryService(PozRepository pozRepository) {
        this.pozRepository = pozRepository;
    }

    /**
     * Return a {@link List} of {@link Poz} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Poz> findByCriteria(PozCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Poz> specification = createSpecification(criteria);
        return pozRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Poz} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Poz> findByCriteria(PozCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Poz> specification = createSpecification(criteria);
        return pozRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PozCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Poz> specification = createSpecification(criteria);
        return pozRepository.count(specification);
    }

    /**
     * Function to convert PozCriteria to a {@link Specification}
     */
    private Specification<Poz> createSpecification(PozCriteria criteria) {
        Specification<Poz> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Poz_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Poz_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Poz_.aciklama));
            }
            if (criteria.getKisaltma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKisaltma(), Poz_.kisaltma));
            }
            if (criteria.getYil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYil(), Poz_.yil));
            }
            if (criteria.getTenzilatsizFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenzilatsizFiyat(), Poz_.tenzilatsizFiyat));
            }
            if (criteria.getTenzilatliFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenzilatliFiyat(), Poz_.tenzilatliFiyat));
            }
            if (criteria.getTaseronFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaseronFiyat(), Poz_.taseronFiyat));
            }
            if (criteria.getTasereFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTasereFiyat(), Poz_.tasereFiyat));
            }
            if (criteria.getMalzemeMi() != null) {
                specification = specification.and(buildSpecification(criteria.getMalzemeMi(), Poz_.malzemeMi));
            }
            if (criteria.getHakedisDetayId() != null) {
                specification = specification.and(buildSpecification(criteria.getHakedisDetayId(),
                    root -> root.join(Poz_.hakedisDetays, JoinType.LEFT).get(HakedisDetay_.id)));
            }
            if (criteria.getBirimId() != null) {
                specification = specification.and(buildSpecification(criteria.getBirimId(),
                    root -> root.join(Poz_.birim, JoinType.LEFT).get(Birim_.id)));
            }
            if (criteria.getGrupId() != null) {
                specification = specification.and(buildSpecification(criteria.getGrupId(),
                    root -> root.join(Poz_.grups, JoinType.LEFT).get(PozGrubu_.id)));
            }
        }
        return specification;
    }
}

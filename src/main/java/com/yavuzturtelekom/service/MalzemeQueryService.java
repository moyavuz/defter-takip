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

import com.yavuzturtelekom.domain.Malzeme;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.MalzemeRepository;
import com.yavuzturtelekom.service.dto.MalzemeCriteria;

/**
 * Service for executing complex queries for Malzeme entities in the database.
 * The main input is a {@link MalzemeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Malzeme} or a {@link Page} of {@link Malzeme} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MalzemeQueryService extends QueryService<Malzeme> {

    private final Logger log = LoggerFactory.getLogger(MalzemeQueryService.class);

    private final MalzemeRepository malzemeRepository;

    public MalzemeQueryService(MalzemeRepository malzemeRepository) {
        this.malzemeRepository = malzemeRepository;
    }

    /**
     * Return a {@link List} of {@link Malzeme} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Malzeme> findByCriteria(MalzemeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Malzeme> specification = createSpecification(criteria);
        return malzemeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Malzeme} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Malzeme> findByCriteria(MalzemeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Malzeme> specification = createSpecification(criteria);
        return malzemeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MalzemeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Malzeme> specification = createSpecification(criteria);
        return malzemeRepository.count(specification);
    }

    /**
     * Function to convert MalzemeCriteria to a {@link Specification}
     */
    private Specification<Malzeme> createSpecification(MalzemeCriteria criteria) {
        Specification<Malzeme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Malzeme_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Malzeme_.ad));
            }
            if (criteria.getMalzemeNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMalzemeNo(), Malzeme_.malzemeNo));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Malzeme_.aciklama));
            }
            if (criteria.getKisaltma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKisaltma(), Malzeme_.kisaltma));
            }
            if (criteria.getTenzilatsizFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenzilatsizFiyat(), Malzeme_.tenzilatsizFiyat));
            }
            if (criteria.getTenzilatliFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTenzilatliFiyat(), Malzeme_.tenzilatliFiyat));
            }
            if (criteria.getTaseronFiyat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaseronFiyat(), Malzeme_.taseronFiyat));
            }
            if (criteria.getParaBirimi() != null) {
                specification = specification.and(buildSpecification(criteria.getParaBirimi(), Malzeme_.paraBirimi));
            }
            if (criteria.getStokTakipId() != null) {
                specification = specification.and(buildSpecification(criteria.getStokTakipId(),
                    root -> root.join(Malzeme_.stokTakips, JoinType.LEFT).get(StokTakip_.id)));
            }
            if (criteria.getBirimId() != null) {
                specification = specification.and(buildSpecification(criteria.getBirimId(),
                    root -> root.join(Malzeme_.birim, JoinType.LEFT).get(Birim_.id)));
            }
            if (criteria.getDepoId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepoId(),
                    root -> root.join(Malzeme_.depo, JoinType.LEFT).get(Depo_.id)));
            }
            if (criteria.getGrupId() != null) {
                specification = specification.and(buildSpecification(criteria.getGrupId(),
                    root -> root.join(Malzeme_.grups, JoinType.LEFT).get(MalzemeGrubu_.id)));
            }
        }
        return specification;
    }
}

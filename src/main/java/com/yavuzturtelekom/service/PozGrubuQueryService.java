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

import com.yavuzturtelekom.domain.PozGrubu;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PozGrubuRepository;
import com.yavuzturtelekom.service.dto.PozGrubuCriteria;

/**
 * Service for executing complex queries for PozGrubu entities in the database.
 * The main input is a {@link PozGrubuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PozGrubu} or a {@link Page} of {@link PozGrubu} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PozGrubuQueryService extends QueryService<PozGrubu> {

    private final Logger log = LoggerFactory.getLogger(PozGrubuQueryService.class);

    private final PozGrubuRepository pozGrubuRepository;

    public PozGrubuQueryService(PozGrubuRepository pozGrubuRepository) {
        this.pozGrubuRepository = pozGrubuRepository;
    }

    /**
     * Return a {@link List} of {@link PozGrubu} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PozGrubu> findByCriteria(PozGrubuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PozGrubu> specification = createSpecification(criteria);
        return pozGrubuRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PozGrubu} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PozGrubu> findByCriteria(PozGrubuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PozGrubu> specification = createSpecification(criteria);
        return pozGrubuRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PozGrubuCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PozGrubu> specification = createSpecification(criteria);
        return pozGrubuRepository.count(specification);
    }

    /**
     * Function to convert PozGrubuCriteria to a {@link Specification}
     */
    private Specification<PozGrubu> createSpecification(PozGrubuCriteria criteria) {
        Specification<PozGrubu> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PozGrubu_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), PozGrubu_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), PozGrubu_.aciklama));
            }
            if (criteria.getKisaltma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKisaltma(), PozGrubu_.kisaltma));
            }
            if (criteria.getPozListesiId() != null) {
                specification = specification.and(buildSpecification(criteria.getPozListesiId(),
                    root -> root.join(PozGrubu_.pozListesis, JoinType.LEFT).get(Poz_.id)));
            }
        }
        return specification;
    }
}

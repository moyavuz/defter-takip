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

import com.yavuzturtelekom.domain.Birim;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.BirimRepository;
import com.yavuzturtelekom.service.dto.BirimCriteria;

/**
 * Service for executing complex queries for Birim entities in the database.
 * The main input is a {@link BirimCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Birim} or a {@link Page} of {@link Birim} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BirimQueryService extends QueryService<Birim> {

    private final Logger log = LoggerFactory.getLogger(BirimQueryService.class);

    private final BirimRepository birimRepository;

    public BirimQueryService(BirimRepository birimRepository) {
        this.birimRepository = birimRepository;
    }

    /**
     * Return a {@link List} of {@link Birim} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Birim> findByCriteria(BirimCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Birim> specification = createSpecification(criteria);
        return birimRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Birim} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Birim> findByCriteria(BirimCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Birim> specification = createSpecification(criteria);
        return birimRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BirimCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Birim> specification = createSpecification(criteria);
        return birimRepository.count(specification);
    }

    /**
     * Function to convert BirimCriteria to a {@link Specification}
     */
    private Specification<Birim> createSpecification(BirimCriteria criteria) {
        Specification<Birim> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Birim_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Birim_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), Birim_.aciklama));
            }
            if (criteria.getKisaltma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKisaltma(), Birim_.kisaltma));
            }
            if (criteria.getCarpan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCarpan(), Birim_.carpan));
            }
        }
        return specification;
    }
}

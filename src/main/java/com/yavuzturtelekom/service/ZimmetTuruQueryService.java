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

import com.yavuzturtelekom.domain.ZimmetTuru;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.ZimmetTuruRepository;
import com.yavuzturtelekom.service.dto.ZimmetTuruCriteria;

/**
 * Service for executing complex queries for ZimmetTuru entities in the database.
 * The main input is a {@link ZimmetTuruCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ZimmetTuru} or a {@link Page} of {@link ZimmetTuru} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ZimmetTuruQueryService extends QueryService<ZimmetTuru> {

    private final Logger log = LoggerFactory.getLogger(ZimmetTuruQueryService.class);

    private final ZimmetTuruRepository zimmetTuruRepository;

    public ZimmetTuruQueryService(ZimmetTuruRepository zimmetTuruRepository) {
        this.zimmetTuruRepository = zimmetTuruRepository;
    }

    /**
     * Return a {@link List} of {@link ZimmetTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ZimmetTuru> findByCriteria(ZimmetTuruCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ZimmetTuru> specification = createSpecification(criteria);
        return zimmetTuruRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ZimmetTuru} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ZimmetTuru> findByCriteria(ZimmetTuruCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ZimmetTuru> specification = createSpecification(criteria);
        return zimmetTuruRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ZimmetTuruCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ZimmetTuru> specification = createSpecification(criteria);
        return zimmetTuruRepository.count(specification);
    }

    /**
     * Function to convert ZimmetTuruCriteria to a {@link Specification}
     */
    private Specification<ZimmetTuru> createSpecification(ZimmetTuruCriteria criteria) {
        Specification<ZimmetTuru> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ZimmetTuru_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), ZimmetTuru_.ad));
            }
            if (criteria.getAciklama() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAciklama(), ZimmetTuru_.aciklama));
            }
        }
        return specification;
    }
}

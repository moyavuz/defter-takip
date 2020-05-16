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

import com.yavuzturtelekom.domain.Ekip;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.EkipRepository;
import com.yavuzturtelekom.service.dto.EkipCriteria;

/**
 * Service for executing complex queries for Ekip entities in the database.
 * The main input is a {@link EkipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ekip} or a {@link Page} of {@link Ekip} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EkipQueryService extends QueryService<Ekip> {

    private final Logger log = LoggerFactory.getLogger(EkipQueryService.class);

    private final EkipRepository ekipRepository;

    public EkipQueryService(EkipRepository ekipRepository) {
        this.ekipRepository = ekipRepository;
    }

    /**
     * Return a {@link List} of {@link Ekip} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ekip> findByCriteria(EkipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ekip> specification = createSpecification(criteria);
        return ekipRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Ekip} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ekip> findByCriteria(EkipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ekip> specification = createSpecification(criteria);
        return ekipRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EkipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ekip> specification = createSpecification(criteria);
        return ekipRepository.count(specification);
    }

    /**
     * Function to convert EkipCriteria to a {@link Specification}
     */
    private Specification<Ekip> createSpecification(EkipCriteria criteria) {
        Specification<Ekip> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ekip_.id));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Ekip_.ad));
            }
            if (criteria.getTelefon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefon(), Ekip_.telefon));
            }
            if (criteria.getEposta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEposta(), Ekip_.eposta));
            }
            if (criteria.getTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getTuru(), Ekip_.turu));
            }
            if (criteria.getHakedisId() != null) {
                specification = specification.and(buildSpecification(criteria.getHakedisId(),
                    root -> root.join(Ekip_.hakedis, JoinType.LEFT).get(Hakedis_.id)));
            }
            if (criteria.getStokTakipId() != null) {
                specification = specification.and(buildSpecification(criteria.getStokTakipId(),
                    root -> root.join(Ekip_.stokTakips, JoinType.LEFT).get(StokTakip_.id)));
            }
            if (criteria.getEkipSorumluId() != null) {
                specification = specification.and(buildSpecification(criteria.getEkipSorumluId(),
                    root -> root.join(Ekip_.ekipSorumlu, JoinType.LEFT).get(Personel_.id)));
            }
            if (criteria.getMudurlukId() != null) {
                specification = specification.and(buildSpecification(criteria.getMudurlukId(),
                    root -> root.join(Ekip_.mudurluk, JoinType.LEFT).get(Mudurluk_.id)));
            }
            if (criteria.getEkipPersonelId() != null) {
                specification = specification.and(buildSpecification(criteria.getEkipPersonelId(),
                    root -> root.join(Ekip_.ekipPersonels, JoinType.LEFT).get(Personel_.id)));
            }
        }
        return specification;
    }
}

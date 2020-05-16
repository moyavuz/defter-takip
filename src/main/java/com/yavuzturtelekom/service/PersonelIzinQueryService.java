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

import com.yavuzturtelekom.domain.PersonelIzin;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PersonelIzinRepository;
import com.yavuzturtelekom.service.dto.PersonelIzinCriteria;

/**
 * Service for executing complex queries for PersonelIzin entities in the database.
 * The main input is a {@link PersonelIzinCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonelIzin} or a {@link Page} of {@link PersonelIzin} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonelIzinQueryService extends QueryService<PersonelIzin> {

    private final Logger log = LoggerFactory.getLogger(PersonelIzinQueryService.class);

    private final PersonelIzinRepository personelIzinRepository;

    public PersonelIzinQueryService(PersonelIzinRepository personelIzinRepository) {
        this.personelIzinRepository = personelIzinRepository;
    }

    /**
     * Return a {@link List} of {@link PersonelIzin} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonelIzin> findByCriteria(PersonelIzinCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonelIzin> specification = createSpecification(criteria);
        return personelIzinRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PersonelIzin} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonelIzin> findByCriteria(PersonelIzinCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonelIzin> specification = createSpecification(criteria);
        return personelIzinRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonelIzinCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonelIzin> specification = createSpecification(criteria);
        return personelIzinRepository.count(specification);
    }

    /**
     * Function to convert PersonelIzinCriteria to a {@link Specification}
     */
    private Specification<PersonelIzin> createSpecification(PersonelIzinCriteria criteria) {
        Specification<PersonelIzin> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PersonelIzin_.id));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), PersonelIzin_.tarih));
            }
            if (criteria.getMiktar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMiktar(), PersonelIzin_.miktar));
            }
            if (criteria.getTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getTuru(), PersonelIzin_.turu));
            }
            if (criteria.getPersonelId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonelId(),
                    root -> root.join(PersonelIzin_.personel, JoinType.LEFT).get(Personel_.id)));
            }
        }
        return specification;
    }
}

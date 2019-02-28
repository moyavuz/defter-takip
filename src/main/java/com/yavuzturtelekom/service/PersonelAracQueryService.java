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

import com.yavuzturtelekom.domain.PersonelArac;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PersonelAracRepository;
import com.yavuzturtelekom.service.dto.PersonelAracCriteria;

/**
 * Service for executing complex queries for PersonelArac entities in the database.
 * The main input is a {@link PersonelAracCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonelArac} or a {@link Page} of {@link PersonelArac} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonelAracQueryService extends QueryService<PersonelArac> {

    private final Logger log = LoggerFactory.getLogger(PersonelAracQueryService.class);

    private final PersonelAracRepository personelAracRepository;

    public PersonelAracQueryService(PersonelAracRepository personelAracRepository) {
        this.personelAracRepository = personelAracRepository;
    }

    /**
     * Return a {@link List} of {@link PersonelArac} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonelArac> findByCriteria(PersonelAracCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonelArac> specification = createSpecification(criteria);
        return personelAracRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PersonelArac} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonelArac> findByCriteria(PersonelAracCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonelArac> specification = createSpecification(criteria);
        return personelAracRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonelAracCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonelArac> specification = createSpecification(criteria);
        return personelAracRepository.count(specification);
    }

    /**
     * Function to convert PersonelAracCriteria to a {@link Specification}
     */
    private Specification<PersonelArac> createSpecification(PersonelAracCriteria criteria) {
        Specification<PersonelArac> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PersonelArac_.id));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), PersonelArac_.tarih));
            }
            if (criteria.getAracId() != null) {
                specification = specification.and(buildSpecification(criteria.getAracId(),
                    root -> root.join(PersonelArac_.arac, JoinType.LEFT).get(Arac_.id)));
            }
            if (criteria.getPersonelId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonelId(),
                    root -> root.join(PersonelArac_.personel, JoinType.LEFT).get(Personel_.id)));
            }
        }
        return specification;
    }
}

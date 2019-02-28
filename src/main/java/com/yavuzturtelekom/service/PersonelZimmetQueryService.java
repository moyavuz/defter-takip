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

import com.yavuzturtelekom.domain.PersonelZimmet;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PersonelZimmetRepository;
import com.yavuzturtelekom.service.dto.PersonelZimmetCriteria;

/**
 * Service for executing complex queries for PersonelZimmet entities in the database.
 * The main input is a {@link PersonelZimmetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonelZimmet} or a {@link Page} of {@link PersonelZimmet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonelZimmetQueryService extends QueryService<PersonelZimmet> {

    private final Logger log = LoggerFactory.getLogger(PersonelZimmetQueryService.class);

    private final PersonelZimmetRepository personelZimmetRepository;

    public PersonelZimmetQueryService(PersonelZimmetRepository personelZimmetRepository) {
        this.personelZimmetRepository = personelZimmetRepository;
    }

    /**
     * Return a {@link List} of {@link PersonelZimmet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonelZimmet> findByCriteria(PersonelZimmetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonelZimmet> specification = createSpecification(criteria);
        return personelZimmetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PersonelZimmet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonelZimmet> findByCriteria(PersonelZimmetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonelZimmet> specification = createSpecification(criteria);
        return personelZimmetRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonelZimmetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonelZimmet> specification = createSpecification(criteria);
        return personelZimmetRepository.count(specification);
    }

    /**
     * Function to convert PersonelZimmetCriteria to a {@link Specification}
     */
    private Specification<PersonelZimmet> createSpecification(PersonelZimmetCriteria criteria) {
        Specification<PersonelZimmet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PersonelZimmet_.id));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), PersonelZimmet_.tarih));
            }
            if (criteria.getDurumu() != null) {
                specification = specification.and(buildSpecification(criteria.getDurumu(), PersonelZimmet_.durumu));
            }
            if (criteria.getPersonelId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonelId(),
                    root -> root.join(PersonelZimmet_.personel, JoinType.LEFT).get(Personel_.id)));
            }
            if (criteria.getZimmetId() != null) {
                specification = specification.and(buildSpecification(criteria.getZimmetId(),
                    root -> root.join(PersonelZimmet_.zimmet, JoinType.LEFT).get(ZimmetTuru_.id)));
            }
        }
        return specification;
    }
}

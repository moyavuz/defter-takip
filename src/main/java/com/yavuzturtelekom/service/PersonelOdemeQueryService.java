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

import com.yavuzturtelekom.domain.PersonelOdeme;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PersonelOdemeRepository;
import com.yavuzturtelekom.service.dto.PersonelOdemeCriteria;

/**
 * Service for executing complex queries for PersonelOdeme entities in the database.
 * The main input is a {@link PersonelOdemeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PersonelOdeme} or a {@link Page} of {@link PersonelOdeme} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonelOdemeQueryService extends QueryService<PersonelOdeme> {

    private final Logger log = LoggerFactory.getLogger(PersonelOdemeQueryService.class);

    private final PersonelOdemeRepository personelOdemeRepository;

    public PersonelOdemeQueryService(PersonelOdemeRepository personelOdemeRepository) {
        this.personelOdemeRepository = personelOdemeRepository;
    }

    /**
     * Return a {@link List} of {@link PersonelOdeme} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PersonelOdeme> findByCriteria(PersonelOdemeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PersonelOdeme> specification = createSpecification(criteria);
        return personelOdemeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PersonelOdeme} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonelOdeme> findByCriteria(PersonelOdemeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PersonelOdeme> specification = createSpecification(criteria);
        return personelOdemeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonelOdemeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PersonelOdeme> specification = createSpecification(criteria);
        return personelOdemeRepository.count(specification);
    }

    /**
     * Function to convert PersonelOdemeCriteria to a {@link Specification}
     */
    private Specification<PersonelOdeme> createSpecification(PersonelOdemeCriteria criteria) {
        Specification<PersonelOdeme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PersonelOdeme_.id));
            }
            if (criteria.getTarih() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTarih(), PersonelOdeme_.tarih));
            }
            if (criteria.getMiktar() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMiktar(), PersonelOdeme_.miktar));
            }
            if (criteria.getTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getTuru(), PersonelOdeme_.turu));
            }
            if (criteria.getYontem() != null) {
                specification = specification.and(buildSpecification(criteria.getYontem(), PersonelOdeme_.yontem));
            }
            if (criteria.getPersonelId() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonelId(),
                    root -> root.join(PersonelOdeme_.personel, JoinType.LEFT).get(Personel_.id)));
            }
        }
        return specification;
    }
}

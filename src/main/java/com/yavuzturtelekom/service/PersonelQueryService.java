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

import com.yavuzturtelekom.domain.Personel;
import com.yavuzturtelekom.domain.*; // for static metamodels
import com.yavuzturtelekom.repository.PersonelRepository;
import com.yavuzturtelekom.service.dto.PersonelCriteria;

/**
 * Service for executing complex queries for Personel entities in the database.
 * The main input is a {@link PersonelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Personel} or a {@link Page} of {@link Personel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PersonelQueryService extends QueryService<Personel> {

    private final Logger log = LoggerFactory.getLogger(PersonelQueryService.class);

    private final PersonelRepository personelRepository;

    public PersonelQueryService(PersonelRepository personelRepository) {
        this.personelRepository = personelRepository;
    }

    /**
     * Return a {@link List} of {@link Personel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Personel> findByCriteria(PersonelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Personel> specification = createSpecification(criteria);
        return personelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Personel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Personel> findByCriteria(PersonelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Personel> specification = createSpecification(criteria);
        return personelRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PersonelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Personel> specification = createSpecification(criteria);
        return personelRepository.count(specification);
    }

    /**
     * Function to convert PersonelCriteria to a {@link Specification}
     */
    private Specification<Personel> createSpecification(PersonelCriteria criteria) {
        Specification<Personel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Personel_.id));
            }
            if (criteria.getTckimlikno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTckimlikno(), Personel_.tckimlikno));
            }
            if (criteria.getAd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAd(), Personel_.ad));
            }
            if (criteria.getCepTelefon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCepTelefon(), Personel_.cepTelefon));
            }
            if (criteria.getSabitTelefon() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSabitTelefon(), Personel_.sabitTelefon));
            }
            if (criteria.getEposta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEposta(), Personel_.eposta));
            }
            if (criteria.getCinsiyet() != null) {
                specification = specification.and(buildSpecification(criteria.getCinsiyet(), Personel_.cinsiyet));
            }
            if (criteria.getEgitimDurumu() != null) {
                specification = specification.and(buildSpecification(criteria.getEgitimDurumu(), Personel_.egitimDurumu));
            }
            if (criteria.getKanGrubu() != null) {
                specification = specification.and(buildSpecification(criteria.getKanGrubu(), Personel_.kanGrubu));
            }
            if (criteria.getPersonelTuru() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonelTuru(), Personel_.personelTuru));
            }
            if (criteria.getUcret() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUcret(), Personel_.ucret));
            }
            if (criteria.getIban() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIban(), Personel_.iban));
            }
            if (criteria.getMedeniHali() != null) {
                specification = specification.and(buildSpecification(criteria.getMedeniHali(), Personel_.medeniHali));
            }
            if (criteria.getDogumTarihi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDogumTarihi(), Personel_.dogumTarihi));
            }
            if (criteria.getGirisTarihi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGirisTarihi(), Personel_.girisTarihi));
            }
            if (criteria.getIzinHakedis() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIzinHakedis(), Personel_.izinHakedis));
            }
            if (criteria.getCikisTarihi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCikisTarihi(), Personel_.cikisTarihi));
            }
            if (criteria.getYoneticiId() != null) {
                specification = specification.and(buildSpecification(criteria.getYoneticiId(),
                    root -> root.join(Personel_.yonetici, JoinType.LEFT).get(Personel_.id)));
            }
            if (criteria.getUnvanId() != null) {
                specification = specification.and(buildSpecification(criteria.getUnvanId(),
                    root -> root.join(Personel_.unvan, JoinType.LEFT).get(Unvan_.id)));
            }
            if (criteria.getEkipId() != null) {
                specification = specification.and(buildSpecification(criteria.getEkipId(),
                    root -> root.join(Personel_.ekips, JoinType.LEFT).get(Ekip_.id)));
            }
        }
        return specification;
    }
}

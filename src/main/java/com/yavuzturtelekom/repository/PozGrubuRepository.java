package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.PozGrubu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PozGrubu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PozGrubuRepository extends JpaRepository<PozGrubu, Long>, JpaSpecificationExecutor<PozGrubu> {

    @Query(value = "select distinct poz_grubu from PozGrubu poz_grubu left join fetch poz_grubu.pozListesis",
        countQuery = "select count(distinct poz_grubu) from PozGrubu poz_grubu")
    Page<PozGrubu> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct poz_grubu from PozGrubu poz_grubu left join fetch poz_grubu.pozListesis")
    List<PozGrubu> findAllWithEagerRelationships();

    @Query("select poz_grubu from PozGrubu poz_grubu left join fetch poz_grubu.pozListesis where poz_grubu.id =:id")
    Optional<PozGrubu> findOneWithEagerRelationships(@Param("id") Long id);

}

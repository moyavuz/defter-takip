package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.MalzemeGrubu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MalzemeGrubu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MalzemeGrubuRepository extends JpaRepository<MalzemeGrubu, Long>, JpaSpecificationExecutor<MalzemeGrubu> {

    @Query(value = "select distinct malzeme_grubu from MalzemeGrubu malzeme_grubu left join fetch malzeme_grubu.malzemeListesis",
        countQuery = "select count(distinct malzeme_grubu) from MalzemeGrubu malzeme_grubu")
    Page<MalzemeGrubu> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct malzeme_grubu from MalzemeGrubu malzeme_grubu left join fetch malzeme_grubu.malzemeListesis")
    List<MalzemeGrubu> findAllWithEagerRelationships();

    @Query("select malzeme_grubu from MalzemeGrubu malzeme_grubu left join fetch malzeme_grubu.malzemeListesis where malzeme_grubu.id =:id")
    Optional<MalzemeGrubu> findOneWithEagerRelationships(@Param("id") Long id);

}

package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Proje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Proje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjeRepository extends JpaRepository<Proje, Long> {

    @Query(value = "select distinct proje from Proje proje left join fetch proje.projeIsciliks",
        countQuery = "select count(distinct proje) from Proje proje")
    Page<Proje> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct proje from Proje proje left join fetch proje.projeIsciliks")
    List<Proje> findAllWithEagerRelationships();

    @Query("select proje from Proje proje left join fetch proje.projeIsciliks where proje.id =:id")
    Optional<Proje> findOneWithEagerRelationships(@Param("id") Long id);

}

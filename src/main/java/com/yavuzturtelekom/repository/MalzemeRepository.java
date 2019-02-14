package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Malzeme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Malzeme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MalzemeRepository extends JpaRepository<Malzeme, Long> {

    @Query(value = "select distinct malzeme from Malzeme malzeme left join fetch malzeme.malzemeIsciliks",
        countQuery = "select count(distinct malzeme) from Malzeme malzeme")
    Page<Malzeme> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct malzeme from Malzeme malzeme left join fetch malzeme.malzemeIsciliks")
    List<Malzeme> findAllWithEagerRelationships();

    @Query("select malzeme from Malzeme malzeme left join fetch malzeme.malzemeIsciliks where malzeme.id =:id")
    Optional<Malzeme> findOneWithEagerRelationships(@Param("id") Long id);

}

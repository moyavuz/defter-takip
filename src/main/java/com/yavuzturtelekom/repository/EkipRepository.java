package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Ekip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Ekip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EkipRepository extends JpaRepository<Ekip, Long> {

    @Query(value = "select distinct ekip from Ekip ekip left join fetch ekip.ekipCalisans",
        countQuery = "select count(distinct ekip) from Ekip ekip")
    Page<Ekip> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct ekip from Ekip ekip left join fetch ekip.ekipCalisans")
    List<Ekip> findAllWithEagerRelationships();

    @Query("select ekip from Ekip ekip left join fetch ekip.ekipCalisans where ekip.id =:id")
    Optional<Ekip> findOneWithEagerRelationships(@Param("id") Long id);

}

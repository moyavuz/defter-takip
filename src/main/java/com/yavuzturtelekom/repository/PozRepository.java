package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Poz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Poz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PozRepository extends JpaRepository<Poz, Long>, JpaSpecificationExecutor<Poz> {

}

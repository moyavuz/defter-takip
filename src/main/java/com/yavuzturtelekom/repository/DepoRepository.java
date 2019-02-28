package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Depo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Depo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepoRepository extends JpaRepository<Depo, Long>, JpaSpecificationExecutor<Depo> {

}

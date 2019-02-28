package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Arac;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Arac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AracRepository extends JpaRepository<Arac, Long>, JpaSpecificationExecutor<Arac> {

}

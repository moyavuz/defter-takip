package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Eskalasyon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Eskalasyon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EskalasyonRepository extends JpaRepository<Eskalasyon, Long>, JpaSpecificationExecutor<Eskalasyon> {

}

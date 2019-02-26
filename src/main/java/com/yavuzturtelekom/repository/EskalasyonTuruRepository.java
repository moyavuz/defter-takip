package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.EskalasyonTuru;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EskalasyonTuru entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EskalasyonTuruRepository extends JpaRepository<EskalasyonTuru, Long> {

}

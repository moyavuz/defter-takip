package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.StokTakip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StokTakip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StokTakipRepository extends JpaRepository<StokTakip, Long>, JpaSpecificationExecutor<StokTakip> {

}

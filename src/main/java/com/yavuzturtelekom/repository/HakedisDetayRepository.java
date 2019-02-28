package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.HakedisDetay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HakedisDetay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HakedisDetayRepository extends JpaRepository<HakedisDetay, Long>, JpaSpecificationExecutor<HakedisDetay> {

}

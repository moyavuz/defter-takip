package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.IscilikDetay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IscilikDetay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IscilikDetayRepository extends JpaRepository<IscilikDetay, Long> {

}

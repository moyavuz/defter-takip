package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Calisan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Calisan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalisanRepository extends JpaRepository<Calisan, Long> {

}

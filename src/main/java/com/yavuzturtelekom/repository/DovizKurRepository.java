package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.DovizKur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DovizKur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DovizKurRepository extends JpaRepository<DovizKur, Long> {

}

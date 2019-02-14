package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Malzeme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Malzeme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MalzemeRepository extends JpaRepository<Malzeme, Long> {

}

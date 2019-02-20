package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.DefterTuru;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DefterTuru entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DefterTuruRepository extends JpaRepository<DefterTuru, Long> {

}

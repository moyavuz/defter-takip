package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.ProjeTuru;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjeTuru entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjeTuruRepository extends JpaRepository<ProjeTuru, Long> {

}

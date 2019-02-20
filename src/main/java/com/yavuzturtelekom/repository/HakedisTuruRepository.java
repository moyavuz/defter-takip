package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.HakedisTuru;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the HakedisTuru entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HakedisTuruRepository extends JpaRepository<HakedisTuru, Long> {

}

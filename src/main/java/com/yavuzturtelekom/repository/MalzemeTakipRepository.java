package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.MalzemeTakip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MalzemeTakip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MalzemeTakipRepository extends JpaRepository<MalzemeTakip, Long> {

}

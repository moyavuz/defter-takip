package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Hakedis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hakedis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HakedisRepository extends JpaRepository<Hakedis, Long> {

}

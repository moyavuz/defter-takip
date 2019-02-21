package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Santral;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Santral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SantralRepository extends JpaRepository<Santral, Long> {

}

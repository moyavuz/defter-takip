package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Bolge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bolge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BolgeRepository extends JpaRepository<Bolge, Long> {

}

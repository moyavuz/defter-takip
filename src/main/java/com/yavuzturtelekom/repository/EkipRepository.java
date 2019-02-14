package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Ekip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ekip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EkipRepository extends JpaRepository<Ekip, Long> {

}

package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.PersonelArac;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonelArac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonelAracRepository extends JpaRepository<PersonelArac, Long>, JpaSpecificationExecutor<PersonelArac> {

}

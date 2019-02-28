package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.PersonelOdeme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonelOdeme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonelOdemeRepository extends JpaRepository<PersonelOdeme, Long>, JpaSpecificationExecutor<PersonelOdeme> {

}

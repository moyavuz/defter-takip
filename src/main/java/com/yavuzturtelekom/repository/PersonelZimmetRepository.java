package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.PersonelZimmet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonelZimmet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonelZimmetRepository extends JpaRepository<PersonelZimmet, Long>, JpaSpecificationExecutor<PersonelZimmet> {

}

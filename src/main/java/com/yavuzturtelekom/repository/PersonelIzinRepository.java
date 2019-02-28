package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.PersonelIzin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PersonelIzin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonelIzinRepository extends JpaRepository<PersonelIzin, Long>, JpaSpecificationExecutor<PersonelIzin> {

}

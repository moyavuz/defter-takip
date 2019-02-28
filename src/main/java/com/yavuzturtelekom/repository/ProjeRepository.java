package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Proje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Proje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjeRepository extends JpaRepository<Proje, Long>, JpaSpecificationExecutor<Proje> {

}

package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Unvan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Unvan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnvanRepository extends JpaRepository<Unvan, Long>, JpaSpecificationExecutor<Unvan> {

}

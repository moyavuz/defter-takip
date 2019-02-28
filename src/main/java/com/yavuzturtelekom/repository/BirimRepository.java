package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Birim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Birim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BirimRepository extends JpaRepository<Birim, Long>, JpaSpecificationExecutor<Birim> {

}

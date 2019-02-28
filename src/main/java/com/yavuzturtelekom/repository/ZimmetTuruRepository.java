package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.ZimmetTuru;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZimmetTuru entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZimmetTuruRepository extends JpaRepository<ZimmetTuru, Long>, JpaSpecificationExecutor<ZimmetTuru> {

}

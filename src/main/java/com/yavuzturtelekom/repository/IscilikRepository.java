package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Iscilik;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Iscilik entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IscilikRepository extends JpaRepository<Iscilik, Long> {

}

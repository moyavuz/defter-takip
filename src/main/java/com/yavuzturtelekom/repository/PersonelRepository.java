package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Personel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Personel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {

}

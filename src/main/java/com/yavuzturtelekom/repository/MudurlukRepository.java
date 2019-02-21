package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Mudurluk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mudurluk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MudurlukRepository extends JpaRepository<Mudurluk, Long> {

}

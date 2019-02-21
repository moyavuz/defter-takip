package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Il;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Il entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IlRepository extends JpaRepository<Il, Long> {

}

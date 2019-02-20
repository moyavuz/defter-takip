package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Departman;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Departman entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmanRepository extends JpaRepository<Departman, Long> {

}

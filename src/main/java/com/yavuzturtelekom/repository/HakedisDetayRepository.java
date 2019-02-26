package com.yavuzturtelekom.repository;

import com.yavuzturtelekom.domain.Hakedis;
import com.yavuzturtelekom.domain.HakedisDetay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the HakedisDetay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HakedisDetayRepository extends JpaRepository<HakedisDetay, Long> {

    List<HakedisDetay> findHakedisDetayByHakedis(Hakedis hakedis);
}

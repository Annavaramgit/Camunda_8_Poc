package com.aus.corsafe.repository;

import com.aus.corsafe.entity.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor,Integer> {

    @Query("SELECT a FROM Auditor a WHERE CAST(a.nearByArea AS string) LIKE %:area% ORDER BY a.currentTaskCount ASC")
    List<Auditor> findByNearByAreaContaining(@Param("area") String area);

    Optional<Auditor> findByName(String Name);
    Optional<Auditor> findById(Integer id);
}

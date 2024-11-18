package com.aus.corsafe.repository;

import com.aus.corsafe.entity.auditrequest.ProcessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessDetailsRepository extends JpaRepository<ProcessDetails,Integer> {
   public Optional<ProcessDetails> findAllByOrderId(Integer orderId);
}

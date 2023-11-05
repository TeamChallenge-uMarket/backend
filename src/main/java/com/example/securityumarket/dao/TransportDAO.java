package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Transport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportDAO extends JpaRepository<Transport, Long>, JpaSpecificationExecutor<Transport> {
  
    @Query("SELECT c FROM Transport c ORDER BY c.created DESC LIMIT 20")
    Optional<List<Transport>> findNewTransports(Specification<Transport> spec);

    Optional<Transport> findById(long id);
}

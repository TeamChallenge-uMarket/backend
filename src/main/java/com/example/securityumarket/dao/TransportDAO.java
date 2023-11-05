package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Transport;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransportDAO extends JpaRepository<Transport, Long>, JpaSpecificationExecutor<Transport> {

    Optional<Transport> findById(long id);

    @Modifying
    @Query("DELETE FROM Transport t WHERE t.status = 'DELETED' AND t.lastUpdate < :lastUpdate")
    void deleteDeletedTransportsOlderThanOneMonth(@Param("lastUpdate") LocalDateTime lastUpdate);
}

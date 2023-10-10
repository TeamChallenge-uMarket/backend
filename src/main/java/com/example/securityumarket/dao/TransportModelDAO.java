package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.TransportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransportModelDAO extends JpaRepository<TransportModel, Long> {

    @Query("select cm from TransportModel cm where cm.transportTypeBrand.transportBrand.id =:brandId " +
            "AND cm.transportTypeBrand.transportType.id =:typeId ")
    Optional<List<TransportModel>> findAllByTransportTypeAndBrand(@Param("brandId") long brandId, @Param("typeId") long type);

    @Query("select cm from TransportModel cm where cm.transportTypeBrand.transportBrand.id =:brandId")
    Optional<List<TransportModel>> findAllByTransportBrand(@Param("brandId") long brandId);

    Optional<TransportModel> findByModel(String model);
}

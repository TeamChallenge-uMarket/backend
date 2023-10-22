package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.TransportBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportBrandDAO extends JpaRepository<TransportBrand, Long> {

    @Query("SELECT tb FROM TransportBrand tb JOIN tb.transportTypeBrands ttb WHERE ttb.transportType.id =:typeId")
    Optional<List<TransportBrand>> findAllByTransportTypeId(@Param("typeId")Long id);
}

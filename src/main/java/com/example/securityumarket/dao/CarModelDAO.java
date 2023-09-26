package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarModelDAO extends JpaRepository<CarModel, Long> {
    @Query("select cm from CarModel cm where cm.carBrand.id = :brandId")
    List<CarModel> findAllByCarBrand(@Param("brandId") long brandId);

    Optional<CarModel> findByModel(String model);
}

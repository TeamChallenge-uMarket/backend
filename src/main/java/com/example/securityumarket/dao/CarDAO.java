package com.example.securityumarket.dao;

import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.entities.Car;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarDAO extends JpaRepository<Car, Long> {

    @Query("select c from Car c order by c.created desc")
    List<Car> findNewCars(PageRequest pageRequest);

    //TODO need to check
    @Query("select c from Car c " +
            "where c.carModel.carBrand.carType.id = :#{#requestSearch.typeId} " +
            "and c.carModel.carBrand.id = :#{#requestSearch.brandId} " +
            "and c.carModel.id = :#{#requestSearch.modelId} " +
            "order by :#{requestSearch.sort} ")
    List<Car> findCarsByRequest(@Param("requestSearch") RequestCarSearchDTO requestSearch, PageRequest pageRequest);
}

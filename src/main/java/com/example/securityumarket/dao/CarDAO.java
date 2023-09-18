package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Car;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarDAO extends JpaRepository<Car, Long> {

    @Query("select c from Car c order by c.created desc")
    List<Car> findNewCars(PageRequest pageRequest);
}

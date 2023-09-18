package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.CarView;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarViewDAO extends JpaRepository<CarView, Long> {

    //TODO need check this query
    @Query("select cw.car from CarView cw group by cw.car order by count(cw.car) desc")
    List<Car> findPopularCars(PageRequest pageRequest);
}

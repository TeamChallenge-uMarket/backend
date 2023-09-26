package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.CarView;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarViewDAO extends JpaRepository<CarView, Long> {

    @Query("select cw.car from CarView cw group by cw.car order by count(cw.car) desc")
    List<Car> findPopularCars(PageRequest pageRequest);

    @Query("select cw.car from CarView cw where cw.user = :user order by cw.lastUpdate desc")
    List<Car> findViewedCarsByRegisteredUser(@Param("user") Users user, PageRequest pageRequest);
}

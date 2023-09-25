package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.CarFavorite;
import com.example.securityumarket.models.entities.Users;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarFavoriteDAO extends JpaRepository<CarFavorite, Long> {
    @Query("select cf.car from CarFavorite cf where cf.user = :user order by cf.created desc")
    List<Car> findFavorites(@Param("user") Users user, PageRequest pageRequest);
}

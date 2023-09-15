package com.example.securityumarket.dao;

import com.example.securityumarket.models.entities.CarFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarFavoriteDAO extends JpaRepository<CarFavorite, Long> {
}

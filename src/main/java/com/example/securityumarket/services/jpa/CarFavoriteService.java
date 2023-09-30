package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarFavoriteDAO;
import com.example.securityumarket.exception.UAutoException;
import com.example.securityumarket.models.entities.Car;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarFavoriteService {
    private final CarFavoriteDAO carFavoriteDAO;

    public List<Car> findFavorites(Users user, PageRequest of) {
        return carFavoriteDAO.findFavorites(user, of)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new UAutoException("Favorites cars not found"));
    }
}

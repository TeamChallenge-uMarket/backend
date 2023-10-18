package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.FavoriteTransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.FavoriteTransport;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteTransportService {
    private final FavoriteTransportDAO favoriteTransportDAO;

    public List<Transport> findFavorites(Users user) {
        return favoriteTransportDAO.findFavorites(user)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Favorite transport"));
    }

    public void addFavorite(Users users, Transport transport) {
        favoriteTransportDAO.save(FavoriteTransport.builder()
                .user(users)
                .transport(transport)
                .build());
    }
}

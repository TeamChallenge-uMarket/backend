package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.FavoriteTransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.FavoriteTransport;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Boolean isFavoriteByUser(Users user, Transport transport) {
        return favoriteTransportDAO.existsByUserAndTransport(user, transport);
    }

    @Transactional
    public void deleteFromFavoriteByUserAndTransport(Users user, Transport transport) {
        favoriteTransportDAO.deleteByUserAndTransport(user, transport);
    }

    public Integer countByTransport(Transport transport) {
        return favoriteTransportDAO.countAllByTransport(transport);
    }

    public void addFavorite(Users authenticatedUser, Transport transport) {
        favoriteTransportDAO.save(FavoriteTransport.builder()
                .user(authenticatedUser)
                .transport(transport)
                .build());
    }
}

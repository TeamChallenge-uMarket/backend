package com.example.securityumarket.services.pages;

import com.example.securityumarket.models.DTO.pages.transport.TransportDetailsResponse;
import com.example.securityumarket.models.DTO.transports.TransportDTO;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.FavoriteTransportService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.TransportViewService;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransportPageService {

    private final TransportService transportService;
    private final TransportViewService transportViewService;
    private final FavoriteTransportService favoriteTransportService;
    private final UserService userService;

    public ResponseEntity<? extends TransportDTO> getTransport(Long transportId) {
        return transportService.getTransportDetails(transportId);
    }

    public TransportDetailsResponse getTransportDetails(Long transportId) {
        Transport transport = transportService.findTransportById(transportId);
        Integer countViews = transportViewService.countByTransport(transport);

        boolean isFavorite = isFavoriteTransport(transport);

        return TransportDetailsResponse.builder()
                .isFavorite(isFavorite)
                .countViews(countViews)
                .build();
    }

    private boolean isFavoriteTransport(Transport transport) {
        if (userService.isUserAuthenticated()) {
            Users authenticatedUser = userService.getAuthenticatedUser();
            return favoriteTransportService.isFavoriteByUser(authenticatedUser, transport);
        }
        else {
            return false;
        }
    }
}

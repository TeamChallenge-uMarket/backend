package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.entities.user.TransportPageUserDetailsDto;
import com.example.securityumarket.dto.pages.transport.TransportDetailsResponse;
import com.example.securityumarket.dto.transports.TransportDTO;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.FavoriteTransportService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.TransportViewService;
import com.example.securityumarket.services.jpa.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransportPageService {

    private final TransportService transportService;

    private final TransportViewService transportViewService;

    private final FavoriteTransportService favoriteTransportService;

    private final UserPageService userPageService;

    private final UserService userService;


    @Transactional
    public ResponseEntity<? extends TransportDTO> getTransport(Long transportId) {
        if (userService.isUserAuthenticated()) {
            addTransportView(transportId);
        }
        return userPageService.getTransportDetails(transportId);
    }

    public TransportDetailsResponse getTransportDetails(Long transportId) {
        Transport transport = transportService.findTransportById(transportId);
        Integer countViews = transportViewService.countByTransport(transport);

        boolean isFavorite = isFavoriteTransport(transport);

        return TransportDetailsResponse.builder()
                .isFavorite(isFavorite)
                .countViews(countViews)
                .created(transport.getCreated())
                .lastUpdated(transport.getLastUpdate())
                .build();
    }


    private void addTransportView(Long transportId) {
        Transport transport = transportService.findTransportById(transportId);
        Users user = userService.getAuthenticatedUser();
        transportViewService.findByUserAndTransport(user, transport);
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


    public TransportPageUserDetailsDto getTransportPageUserDetails() {
        Users user = userService.getAuthenticatedUser();
        return buildTransportPageUserDetailsDTOFromUser(user);
    }

    private TransportPageUserDetailsDto buildTransportPageUserDetailsDTOFromUser(Users user) {
        TransportPageUserDetailsDto dto = new TransportPageUserDetailsDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCityId((user.getCity() != null) ? (user.getCity().getId()) : null);
        dto.setPhone(user.getPhone());
        dto.setPhotoUrl(user.getPhotoUrl());
        dto.setJoinDate(user.getCreated());
        return dto;
    }
}

package com.example.securityumarket.services.page_service;

import com.example.securityumarket.models.DTO.catalog.MotorcycleFilterDTO;
import com.example.securityumarket.models.DTO.transports.impl.MotorcycleDTO;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.FavoriteTransportService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogPageService {

    private final FavoriteTransportService favoriteTransportService;

    private final UserService userService;

    private final TransportService transportService;

    public ResponseEntity<String> addFavorite(long carId) {
        Users authenticatedUser = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(carId);
        favoriteTransportService.addFavorite(authenticatedUser, transport);
        return ResponseEntity.ok("added to favorite"); // may be need to refactor msg!

    }

    public ResponseEntity<List<MotorcycleDTO>> findMotorcycles(MotorcycleFilterDTO motorcycleFilterDTO,
                                                               int page, int limit) {
        List<Transport> transports = transportService.findMotorcyclesByFilter(motorcycleFilterDTO, PageRequest.of(page, limit));
        List<MotorcycleDTO> motorcycleDTOS = transportService.convertTransportListToMotorcycleDTOList(transports);
        return ResponseEntity.ok(motorcycleDTOS);
    }
}

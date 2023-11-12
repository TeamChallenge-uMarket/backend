package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportViewDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportViewService {

    private final TransportViewDAO transportViewDAO;


    public List<Transport> findPopularTransport() {
        return transportViewDAO.findPopularTransport()
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Popular transports"));
    }

    public List<Transport> findPopularTransportByTypeId( long id) {
        return transportViewDAO.findPopularTransportByTypeId(id)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Popular transports by type"));
    }

    public List<Transport> findViewedCarsByRegisteredUser(Users user) {
        return transportViewDAO.findViewedCarsByRegisteredUser(user)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Popular transports by user"));
    }
}

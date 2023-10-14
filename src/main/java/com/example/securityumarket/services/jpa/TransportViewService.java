package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportViewDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportViewService {

    private final TransportViewDAO transportViewDAO;

    public List<Transport> findPopularTransport(PageRequest of) {
        return transportViewDAO.findPopularTransport(of)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Popular transports"));
    }

    public List<Transport> findViewedCarsByRegisteredUser(Users user, PageRequest of) {
        return transportViewDAO.findViewedCarsByRegisteredUser(user, of)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Popular transports"));
    }
}

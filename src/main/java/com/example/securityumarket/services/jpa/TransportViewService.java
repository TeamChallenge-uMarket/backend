package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportViewDAO;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.TransportView;
import com.example.securityumarket.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TransportViewService {

    private final TransportViewDAO transportViewDAO;

    public TransportView save(TransportView transportView) {
        return transportViewDAO.save(transportView);
    }

    public void findByUserAndTransport(Users user, Transport transport) {
        TransportView transportView = transportViewDAO.findByUserAndTransport(user, transport)
                .orElse(buildTransportView(user, transport));
        updateLastUpdated(transportView.getId(), LocalDateTime.now());
        save(transportView);
    }

    private TransportView buildTransportView(Users user, Transport transport) {
        return TransportView.builder()
                .transport(transport)
                .user(user)
                .build();
    }


    public void updateLastUpdated(Long transportViewId, LocalDateTime localDateTime) {
        transportViewDAO.updateLastUpdated(transportViewId, localDateTime);
    }

    public Integer countByTransport(Transport transport) {
        return transportViewDAO.countDistinctByTransport(transport);
    }
}

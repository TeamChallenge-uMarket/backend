package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.HiddenAdDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.HiddenAd;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HiddenAdService {
    private final HiddenAdDAO hiddenAdDAO;

    public void save(Users user, Transport transport) {
        hiddenAdDAO.save(buildHiddenAd(user, transport));
    }

    public List<HiddenAd> findAllByUser(Users user) {
        return hiddenAdDAO.findAllByUser(user);
    }


    private HiddenAd buildHiddenAd(Users user, Transport transport) {
        return HiddenAd.builder()
                .user(user)
                .transport(transport)
                .build();
    }

    public HiddenAd findByUserAndTransport(Users user, Transport transport) {
        return hiddenAdDAO.findByUserAndTransport(user, transport)
                .orElseThrow(() -> new DataNotFoundException("Hidden transport by user and transport"));
    }

    public void delete(HiddenAd hiddenAd) {
        hiddenAdDAO.delete(hiddenAd);
    }
}

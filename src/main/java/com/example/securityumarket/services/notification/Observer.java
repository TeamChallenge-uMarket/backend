package com.example.securityumarket.services.notification;

import com.example.securityumarket.models.entities.Transport;

import java.util.List;

public interface Observer {
    void handleEven(List<Transport> transportList);
}

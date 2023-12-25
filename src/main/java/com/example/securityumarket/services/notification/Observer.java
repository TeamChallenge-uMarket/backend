package com.example.securityumarket.services.notification;

import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;

import java.util.List;

public interface Observer {
    void sendNotification(List<Users> users, Transport transportList);
}

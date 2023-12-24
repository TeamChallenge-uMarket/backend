package com.example.securityumarket.services.notification;

import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.util.EmailUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService implements Observer{

    private final UserService userService;
    private final EmailUtil emailUtil;

    @Override
    public void handleEven(List<Transport> transportList) {

    }
}

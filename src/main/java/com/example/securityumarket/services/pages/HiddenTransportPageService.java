package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.pages.hidden.response.HiddenTransportResponse;
import com.example.securityumarket.dto.pages.hidden.response.HiddenUserResponse;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.HiddenAd;
import com.example.securityumarket.models.HiddenUser;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.HiddenAdService;
import com.example.securityumarket.services.jpa.HiddenUserService;
import com.example.securityumarket.services.jpa.TransportService;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class HiddenTransportPageService {

    private final TransportService transportService;

    private final TransportConverter transportConverter;

    private final UserService userService;

    private final HiddenUserService hiddenUserService;

    private final HiddenAdService hiddenAdService;


    public List<HiddenTransportResponse> getAllHiddenTransport() {
        Users user = userService.getAuthenticatedUser();
        List<HiddenAd> hiddenTransports = hiddenAdService.findAllByUser(user);
        return hiddenTransports.stream()
                .map(this::buildHiddenTransportResponseByHiddenAd)
                .toList();
    }

    public List<HiddenUserResponse> getAllHiddenUsers() {
        Users user = userService.getAuthenticatedUser();
        List<HiddenUser> hiddenUsers = hiddenUserService.findAllByUser(user);
        return hiddenUsers.stream()
                .map(this::buildHiddenUserResponseByHiddenUser)
                .toList();
    }

    public void hideTransport(Long transportId) {
        Users user = userService.getAuthenticatedUser();

        Transport transport = transportService.findTransportById(transportId);
        hiddenAdService.save(user, transport);

        log.info("User with ID {} hid transport with ID {}", user.getId(), transportId);
    }

    @Transactional
    public void unhideTransport(Long transportId) {
        Users user = userService.getAuthenticatedUser();

        Transport transport = transportService.findTransportById(transportId);
        HiddenAd hiddenAd = hiddenAdService.findByUserAndTransport(user, transport);

        hiddenAdService.delete(hiddenAd);

        log.info("User with ID {} unhid transport with ID {}", user.getId(), transportId);
    }

    public void hideAllTransport(Long transportId) {
        Users user = userService.getAuthenticatedUser();

        Transport transport = transportService.findTransportById(transportId);
        Users hiddenUser = transport.getUser();

        hiddenUserService.save(user, hiddenUser);

        log.info("User with ID {} hid transport with ID {}", user.getId(), transportId);
    }

    @Transactional
    public void unhideAllTransport(Long transportId) {
        Users user = userService.getAuthenticatedUser();

        Transport transport = transportService.findTransportById(transportId);
        Users hiddenUser = transport.getUser();

        HiddenUser hiddenUserByUserAndHiddenUser = hiddenUserService.findByUserAndHiddenUser(user, hiddenUser)
                .orElseThrow(() -> new DataNotFoundException("HiddenUser by user and hiddenUser"));

        hiddenUserService.delete(hiddenUserByUserAndHiddenUser);

        log.info("User with ID {} unhid transport with ID {}", user.getId(), transportId);
    }

    public void hideAllTransportByUser(Long userId) {
        Users user = userService.getAuthenticatedUser();

        Users hiddenUser = userService.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User by hidden page"));

        hiddenUserService.findByUserAndHiddenUser(user, hiddenUser)
                .orElseGet(() -> hiddenUserService.save(user, hiddenUser));

        log.info("User with ID {} hid all transport of user with ID {}", user.getId(), hiddenUser.getId());
    }

    public void unhideAllTransportByUserId(Long userId) {
        Users user = userService.getAuthenticatedUser();

        Users hiddenUser = userService.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User by hidden page"));

        HiddenUser hiddenUserByUserAndHiddenUser = hiddenUserService.findByUserAndHiddenUser(user, hiddenUser)
                .orElseThrow(() -> new DataNotFoundException("HiddenUser by user and hiddenUser"));

        hiddenUserService.delete(hiddenUserByUserAndHiddenUser);

        log.info("User with ID {} unhid all transport of user with ID {}", user.getId(), hiddenUser.getId());
    }


    private HiddenTransportResponse buildHiddenTransportResponseByHiddenAd(HiddenAd hiddenTransports) {
        return HiddenTransportResponse.builder()
                .id(hiddenTransports.getId())
                .transport(transportConverter.convertTransportToResponseSearch(hiddenTransports.getTransport()))
                .created(LocalDateTime.now())
                .build();
    }

    private HiddenUserResponse buildHiddenUserResponseByHiddenUser(HiddenUser hiddenUser) {
        return HiddenUserResponse.builder()
                .id(hiddenUser.getId())
                .userId(hiddenUser.getHiddenUser().getId())
                .userPhoto(hiddenUser.getUser().getPhotoUrl())
                .created(LocalDateTime.now())
                .build();
    }
}

package com.example.securityumarket.services.subscriptions.impl;

import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.SearchHistory;
import com.example.securityumarket.models.entities.Users;
import com.example.securityumarket.services.jpa.SearchHistoryService;
import com.example.securityumarket.services.jpa.UserService;
import com.example.securityumarket.services.subscriptions.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransportSubscriptions implements Observed {

    private final SearchHistoryService searchHistoryService;
    private final UserService userService;

    @Transactional
    public void addSearchHistory(RequestSearchDTO requestSearchDTO) {
        SearchHistory searchSearchHistory = searchHistoryService.findByRequestSearchDTO(requestSearchDTO)
                .orElse(searchHistoryService.createSearchHistoryByRequestSearchDTO(requestSearchDTO));
        notifyObservers(searchSearchHistory);
    }

    public void removeSearchHistory(Long searchHistoryId) {
        SearchHistory searchSearchHistory = searchHistoryService.findById(searchHistoryId)
                .orElseThrow(() -> new DataNotFoundException("The search history by id"));
    }

    @Override
    public void notifyObservers(SearchHistory searchSearchHistory) {
        List<Users> users = searchHistoryService.findUsers(searchSearchHistory);
        for (Users user : users) {
            userService.subscriptionNotify()
        }
    }

    @Override
    public void addObserved(Users user) {
        searchHistoryService.addUser(user);
    }

    @Override
    public void removeObserver(Users user) {
        searchHistoryService.removeUser(user);
    }
}

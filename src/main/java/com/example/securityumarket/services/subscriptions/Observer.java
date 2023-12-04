package com.example.securityumarket.services.subscriptions;

import com.example.securityumarket.models.entities.SearchHistory;

import java.util.List;

public interface Observer {
    void handleEvent(List<SearchHistory> searchHistories);
}

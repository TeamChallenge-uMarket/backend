package com.example.securityumarket.services.subscriptions.impl;

import com.example.securityumarket.models.entities.SearchHistory;
import com.example.securityumarket.services.subscriptions.Observer;

import java.util.List;

public class Subscriber implements Observer {
    @Override
    public void handleEvent(List<SearchHistory> searchHistories) {

    }
}

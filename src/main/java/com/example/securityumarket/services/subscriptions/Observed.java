package com.example.securityumarket.services.subscriptions;

import com.example.securityumarket.models.entities.SearchHistory;
import com.example.securityumarket.models.entities.Users;

public interface Observed {
    void addObserved(Users user);
    void removeObserver(Users user);
    void notifyObservers(SearchHistory searchSearchHistory);
}

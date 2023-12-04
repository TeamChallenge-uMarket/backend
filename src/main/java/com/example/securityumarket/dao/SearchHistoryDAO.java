package com.example.securityumarket.dao;

import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryDAO extends JpaRepository<SearchHistory, Long> {
}

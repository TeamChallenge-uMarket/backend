package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.SearchHistoryDAO;
import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.example.securityumarket.models.entities.SearchHistory;
import com.example.securityumarket.models.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryDAO searchHistoryDAO;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Optional<SearchHistory> findByRequestSearchDTO(RequestSearchDTO requestSearchDTO) {  //TODO
        String sql = "SELECT * FROM search_history " +
                "WHERE transport_type_id = :transportTypeId " +
                "AND brand_id = :brandId " +
                "AND model_id = :modelId " +
                "AND region_id = :regionId " +
                "AND order_by = :orderBy " +
                "AND sort_by = :sortBy";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("transportTypeId", requestSearchDTO.getTransportTypeId())
                .addValue("brandId", requestSearchDTO.getBrandId())
                .addValue("modelId", requestSearchDTO.getModelId())
                .addValue("regionId", requestSearchDTO.getRegionId())
                .addValue("orderBy", requestSearchDTO.getOrderBy().toString())
                .addValue("sortBy", requestSearchDTO.getSortBy().toString());

        List<SearchHistory> searchHistories = jdbcTemplate.query(
                sql,
                params,
                new BeanPropertyRowMapper<>(SearchHistory.class));

        return searchHistories.isEmpty() ? Optional.empty() : Optional.of(searchHistories.get(0));
    }

    private SearchHistory buildSearchHistoryFromRequestSearchDTO(RequestSearchDTO resultSet) {
        return SearchHistory.builder()
                .transportTypeId(resultSet.getTransportTypeId())
                .brandId(resultSet.getBrandId())
                .modelId(resultSet.getModelId())
                .regionId(resultSet.getRegionId())
                .orderBy(resultSet.getOrderBy())
                .sortBy(resultSet.getSortBy())
                .build();
    }

    public SearchHistory createSearchHistoryByRequestSearchDTO(RequestSearchDTO requestSearchDTO) {
        SearchHistory searchHistory = buildSearchHistoryFromRequestSearchDTO(requestSearchDTO);
        return save(searchHistory);
    }

    public SearchHistory save(SearchHistory searchHistory) {
        return searchHistoryDAO.save(searchHistory);
    }

    public Optional<SearchHistory> findById(Long searchHistoryId) {
        return searchHistoryDAO.findById(searchHistoryId);
    }

    public void addUser(Users user) {
        //TODO
    }

    public void removeUser(Users user) {
        //TODO
    }
}

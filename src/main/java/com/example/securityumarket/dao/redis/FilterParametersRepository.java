package com.example.securityumarket.dao.redis;

import com.example.securityumarket.dto.filters.response.FilterParametersResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterParametersRepository extends CrudRepository<FilterParametersResponse, String> {
}

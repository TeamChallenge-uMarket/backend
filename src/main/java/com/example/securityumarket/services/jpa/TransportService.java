package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.TransportDAO;
import com.example.securityumarket.exception.DataNotFoundException;
import com.example.securityumarket.models.DTO.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.models.entities.*;
import com.example.securityumarket.util.converter.transposrt_type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;



@RequiredArgsConstructor
@Service
public class TransportService {

    private final TransportDAO transportDAO;

    private final TransportConverter transportConverter;


    public void save(Transport transport) {
        transportDAO.save(transport);
    }

    public Transport findTransportById(long carId) {
        return transportDAO.findById(carId)
                .orElseThrow(() -> new DataNotFoundException("Transport by id"));
    }

    public List<ResponseSearchDTO> convertTransportListToTransportSearchDTO(List<Transport> transports) {
        return transports.stream()
                .map(transportConverter::convertTransportTransportSearchDTO)
                .collect(Collectors.toList());
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteDeletedTransportsOlderThanOneMonth() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        transportDAO.deleteDeletedTransportsOlderThanOneMonth(oneMonthAgo);
    }


    public List<Transport> findAll(Specification<Transport> specification) {
        return transportDAO.findAll(specification);
    }

    public List<Transport> findAll(Specification<Transport> specification, Pageable pageRequest) {
        Page<Transport> transportPage = transportDAO.findAll(specification, pageRequest);
        return transportPage.getContent();
    }
}
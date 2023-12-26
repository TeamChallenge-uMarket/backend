package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.pages.main.response.*;
import com.example.securityumarket.exception.BadRequestException;
import com.example.securityumarket.dto.pages.catalog.response.ResponseSearchDTO;
import com.example.securityumarket.models.Transport;
import com.example.securityumarket.models.Users;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.securityumarket.dao.specifications.TransportSpecifications.*;
import static com.example.securityumarket.dto.pages.catalog.request.RequestSearchDTO.*;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final TransportService transportService;

    private final TransportModelService transportModelService;

    private final TransportTypeService transportTypeService;

    private final TransportBrandService transportBrandService;

    private final CityService cityService;

    private final UserService userService;

    private final RegionService regionService;

    private final TransportConverter transportConverter;


    private List<ResponseSearchDTO> getResponseTransportDTOList(List<Transport> transports) {
        return transportConverter.convertTransportListToResponseSearchDTO(transports);
    }

    public List<ResponseSearchDTO> getNewTransports() {
        List<Transport> newTransports = findNewTransports();
        return getResponseTransportDTOList(newTransports);
    }

    public List<ResponseSearchDTO> getPopularTransports() {
        List<Transport> popularTransports = findPopularTransport();
        return getResponseTransportDTOList(popularTransports);
    }

    public List<ResponseSearchDTO> getRecentlyViewedTransports() {
        Users user = userService.getAuthenticatedUser();
        List<Transport> viewedCarsByUser = findViewedTransportsByRegisteredUser(user);
        return getResponseTransportDTOList(viewedCarsByUser);
    }

    public List<ResponseSearchDTO> getFavoriteTransport() {
        Users user = userService.getAuthenticatedUser();
        List<Transport> viewedCarsByUser = findFavoriteTransportsByRegisteredUser(user);
        return getResponseTransportDTOList(viewedCarsByUser);
    }

    public List<ResponseTypeDTO> getTypeTransport() {
        return transportTypeService.findAll().stream().map(carType ->
                        ResponseTypeDTO.builder()
                                .typeId(carType.getId())
                                .type(carType.getType())
                                .build())
                .collect(Collectors.toList());
    }

    public List<ResponseBrandDTO> getBrandTransport() {
        return transportBrandService.findAll().stream().map(carBrand ->
                        ResponseBrandDTO.builder()
                                .brandId(carBrand.getId())
                                .brand(carBrand.getBrand())
                                .build())
                .collect(Collectors.toList());
    }

    public List<ResponseModelDTO> getModelTransport(long brandId) {
        return transportModelService.findAllByTransportBrand(brandId).stream()
                .map(carModel -> ResponseModelDTO.builder()
                        .modelId(carModel.getId())
                        .brand(carModel.getTransportTypeBrand()
                                .getTransportBrand()
                                .getBrand())
                        .model(carModel.getModel())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponseBrandDTO> getBrandsByTransportType(Long transportTypeId) {
        if (transportTypeId == null) {
            return getBrandTransport();
        } else {
            return transportBrandService.findAllByTransportTypeId(transportTypeId)
                    .stream()
                    .map(carBrand -> ResponseBrandDTO.builder()
                            .brandId(carBrand.getId())
                            .brand(carBrand.getBrand())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    public List<ResponseModelDTO> getModelsByTransportBrand(Long transportTypeId, Long transportBrandId) {
        if (transportBrandId == null) {
            throw new BadRequestException("transportBrandId required");
        }
        if (transportTypeId != null) {
            return transportModelService.findAllByTransportTypeAndBrand(transportBrandId,
                            transportTypeId).stream()
                    .map(carModel -> ResponseModelDTO.builder()
                            .brand(carModel.getTransportTypeBrand().getTransportBrand().getBrand())
                            .modelId(carModel.getId())
                            .model(carModel.getModel())
                            .build())
                    .collect(Collectors.toList());
        } else {
            return getModelTransport(transportBrandId);
        }
    }

    public List<ResponseRegionDTO> getRegions() {
        return regionService.findAll().stream()
                .map(region -> ResponseRegionDTO.builder()
                        .regionId(region.getId())
                        .region(region.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ResponseCityDTO> getCities(List<Long> regionId) {
        return cityService.findAllByRegionId(regionId).stream().map(city ->
                        ResponseCityDTO.builder()
                                .cityId(city.getId())
                                .city(city.getDescription())
                                .region(city.getRegion().getDescription())
                                .build())
                .collect(Collectors.toList());
    }

    private List<Transport> findNewTransports() {
        Pageable pageable = PageRequest.of(0, 20);
        Specification<Transport> specification = isActive()
                .and(sortBy(SortBy.DESC, OrderBy.CREATED));
        return transportService.findAll(specification, pageable);
    }

    private List<Transport> findPopularTransport() {
        Pageable pageable = PageRequest.of(0, 54);
        Specification<Transport> specification = isActive()
                .and(sortPopularTransports());
        return transportService.findAll(specification, pageable);
    }

    private List<Transport> findViewedTransportsByRegisteredUser(Users user) {
        Pageable pageable = PageRequest.of(0, 20);
        Specification<Transport> specification = isActive()
                .and(findTransportViewedByUser(user));
        return transportService.findAll(specification, pageable);
    }

    private List<Transport> findFavoriteTransportsByRegisteredUser(Users user) {
        Specification<Transport> specification = isActive()
                .and(findFavoriteTransportsByUser(user))
                .and(sortBy(SortBy.DESC, OrderBy.CREATED));
        return transportService.findAll(specification);
    }
}

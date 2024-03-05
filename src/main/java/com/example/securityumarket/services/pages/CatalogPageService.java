package com.example.securityumarket.services.pages;

import com.example.securityumarket.dto.filters.response.FilterParametersResponse;
import com.example.securityumarket.dto.pages.catalog.request.RequestFilterParam;
import com.example.securityumarket.dto.pages.catalog.request.RequestSearch;
import com.example.securityumarket.dto.pages.catalog.response.SearchResponse;
import com.example.securityumarket.dto.pages.catalog.response.TransportSearchResponse;
import com.example.securityumarket.models.*;
import com.example.securityumarket.services.jpa.*;
import com.example.securityumarket.services.redis.FilterParametersService;
import com.example.securityumarket.util.converter.transposrt_type.TransportConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.securityumarket.dao.specifications.TransportSpecifications.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogPageService {

    private final FavoriteTransportService favoriteTransportService;

    private final UserService userService;

    private final TransportService transportService;

    private final TransportConverter transportConverter;

    private final HiddenAdService hiddenAdService;

    private final HiddenUserService hiddenUserService;

    private final FilterParametersService filterParametersService;

    public void addFavorite(Long transportId) {
        Users user = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(transportId);
        favoriteTransportService.addFavorite(user, transport);

        log.info("Transport with ID {} added to favorites for user with ID {} successfully.",
                transport.getId(), user.getId());
    }

    public SearchResponse searchTransports(int page, int limit,
                                           RequestSearch requestSearch) {

        long total = transportService.countByParameters(getSpecificationParam(requestSearch));

        List<Transport> transports = transportService.findAll(getSpecificationParam(requestSearch), PageRequest.of(page, limit));

        List<TransportSearchResponse> transportSearchResponses =
                transportConverter.convertTransportListToResponseSearchDTO(transports);

        return SearchResponse.builder()
                .total(total)
                .transportSearchResponse(transportSearchResponses)
                .build();
    }

    public void removeFavorite(Long carId) {
        Users user = userService.getAuthenticatedUser();
        Transport transport = transportService.findTransportById(carId);
        favoriteTransportService.deleteFromFavoriteByUserAndTransport(user, transport);

        log.info("Transport with ID {} removed from favorites for user with ID {} successfully.",
                transport.getId(), user.getId());
    }

    public FilterParametersResponse getFilterParameters(RequestFilterParam request) {
        return filterParametersService
                .getFilterParameters(request.getTransportTypeId(), request.getTransportBrandsId());
    }

    public Specification<Transport> getSpecificationParam(RequestSearch requestSearch) {
        return Specification.allOf(
                isActive()
                        .and(hasTransportTypeId(requestSearch.getTransportTypeId()))
                        .and(hasBrandId(requestSearch.getBrandId()))
                        .and(hasModelId(requestSearch.getModelId()))
                        .and(hasRegionId(requestSearch.getRegionId()))
                        .and(hasCityId(requestSearch.getCityId()))
                        .and(hasBodyTypeId(requestSearch.getBodyTypeId()))
                        .and(hasFuelTypeId(requestSearch.getFuelTypeId()))
                        .and(hasDriveTypeId(requestSearch.getDriveTypeId()))
                        .and(hasTransmissionId(requestSearch.getTransmissionId()))
                        .and(hasColorId(requestSearch.getColorId()))
                        .and(hasConditionId(requestSearch.getConditionId()))
                        .and(hasNumberAxlesId(requestSearch.getNumberAxlesId()))
                        .and(hasProducingCountryId(requestSearch.getProducingCountryId()))
                        .and(hasWheelConfigurationId(requestSearch.getWheelConfigurationId()))
                        .and(priceFrom(requestSearch.getPriceFrom()))
                        .and(priceTo(requestSearch.getPriceTo()))
                        .and(yearFrom(requestSearch.getYearsFrom()))
                        .and(yearTo(requestSearch.getYearsTo()))
                        .and(mileageFrom(requestSearch.getMileageFrom()))
                        .and(mileageTo(requestSearch.getMileageTo()))
                        .and(enginePowerFrom(requestSearch.getEnginePowerFrom()))
                        .and(enginePowerTo(requestSearch.getEnginePowerTo()))
                        .and(engineDisplacementFrom(requestSearch.getEngineDisplacementFrom()))
                        .and(engineDisplacementTo(requestSearch.getEngineDisplacementTo()))
                        .and(numberOfDoorsFrom(requestSearch.getNumberOfDoorsFrom()))
                        .and(numberOfDoorsTo(requestSearch.getNumberOfDoorsTo()))
                        .and(numberOfSeatsFrom(requestSearch.getNumberOfSeatsFrom()))
                        .and(numberOfSeatsTo(requestSearch.getNumberOfSeatsTo()))
                        .and(loadCapacityFrom(requestSearch.getLoadCapacityFrom()))
                        .and(loadCapacityTo(requestSearch.getLoadCapacityTo()))
                        .and(hasTrade(requestSearch.getTrade()))
                        .and(hasMilitary(requestSearch.getMilitary()))
                        .and(hasUncleared(requestSearch.getUncleared()))
                        .and(hasBargain(requestSearch.getBargain()))
                        .and(hasInstallmentPayment(requestSearch.getInstallmentPayment()))
                        .and(hiddenTransport(getHiddenTransportList()))
                        .and(sortBy(
                                requestSearch.getSortBy(), requestSearch.getOrderBy()))
        );
    }

    private List<Long> getHiddenUserList() {
        if (userService.isUserAuthenticated()) {
            Users user = userService.getAuthenticatedUser();
            return getTransportListFromHiddenUserByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    private List<Long> getHiddenTransportList() {
        if (userService.isUserAuthenticated()) {
            Users user = userService.getAuthenticatedUser();

            List<Long> result = new ArrayList<>(getTransportListFromHiddenAdByUser(user));
            result.addAll(getTransportListFromHiddenUserByUser(user));
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private List<Long> getTransportListFromHiddenAdByUser(Users user) {
        List<HiddenAd> allByUser = hiddenAdService.findAllByUser(user);
        return allByUser.stream()
                .map(hiddenAd -> hiddenAd
                        .getTransport()
                        .getId())
                .toList();
    }

    private List<Long> getTransportListFromHiddenUserByUser(Users user) {
        List<HiddenUser> allByUser = hiddenUserService.findAllByUser(user);
        return allByUser.stream()
                .flatMap(hiddenUser -> hiddenUser
                        .getHiddenUser()
                        .getTransports().stream()
                        .map(Transport::getId))
                .toList();
    }
}

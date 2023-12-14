package com.example.securityumarket.models.specifications;

import com.example.securityumarket.models.entities.Transport;
import com.example.securityumarket.models.entities.TransportView;
import com.example.securityumarket.models.entities.Users;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

import static com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO.OrderBy;
import static com.example.securityumarket.models.DTO.pages.catalog.request.RequestSearchDTO.SortBy;

public class TransportSpecifications {

    public static Specification<Transport> hasTransportTypeId(Long transportTypeId) {
        return (root, query, cb) -> {
            if (transportTypeId != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                Join<Object, Object> typeBrandJoin = transportModelJoin.join("transportTypeBrand");
                Join<Object, Object> typeJoin = typeBrandJoin.join("transportType");
                return cb.equal(typeJoin.get("id"), transportTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasBrandId(List<Long> brandId) {
        return (root, query, cb) -> {
            if (brandId != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                Join<Object, Object> typeBrandJoin = transportModelJoin.join("transportTypeBrand");
                Join<Object, Object> typeJoin = typeBrandJoin.join("transportBrand");
                return typeJoin.get("id").in(brandId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasModelId(List<Long> modelId) {
        return (root, query, cb) -> {
            if (modelId != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                return transportModelJoin.get("id").in(modelId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasRegionId(List<Long> regionId) {
        return (root, query, cb) -> {
            if (regionId != null) {
                Join<Object, Object> transportJoin = root.join("city");
                Join<Object, Object> typeJoin = transportJoin.join("region");
                return typeJoin.get("id").in(regionId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasCityId(List<Long> cityId) {
        return (root, query, cb) -> {
            if (cityId != null) {
                Join<Object, Object> transportJoin = root.join("city");
                return transportJoin.get("id").in(cityId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasBodyTypeId(List<Long> bodyTypeId) {
        return (root, query, cb) -> {
            if (bodyTypeId != null) {
                Join<Object, Object> transportJoin = root.join("bodyType");
                return transportJoin.get("id").in(bodyTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasFuelTypeId(List<Long> fuelTypeId) {
        return (root, query, cb) -> {
            if (fuelTypeId != null) {
                Join<Object, Object> transportJoin = root.join("fuelType");
                return transportJoin.get("id").in(fuelTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasDriveTypeId(List<Long> driveTypeId) {
        return (root, query, cb) -> {
            if (driveTypeId != null) {
                Join<Object, Object> transportJoin = root.join("driveType");
                return transportJoin.get("id").in(driveTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasTransmissionId(List<Long> transmissionId) {
        return (root, query, cb) -> {
            if (transmissionId != null) {
                Join<Object, Object> transportJoin = root.join("transmission");
                return transportJoin.get("id").in(transmissionId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasColorId(List<Long> colorId) {
        return (root, query, cb) -> {
            if (colorId != null) {
                Join<Object, Object> transportJoin = root.join("transportColor");
                return transportJoin.get("id").in(colorId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasConditionId(List<Long> conditionId) {
        return (root, query, cb) -> {
            if (conditionId != null) {
                Join<Object, Object> transportJoin = root.join("transportCondition");
                return transportJoin.get("id").in(conditionId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasNumberAxlesId(List<Long> numberAxlesId) {
        return (root, query, cb) -> {
            if (numberAxlesId != null) {
                Join<Object, Object> transportJoin = root.join("numberAxles");
                return transportJoin.get("id").in(numberAxlesId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasProducingCountryId(List<Long> producingCountryId) {
        return (root, query, cb) -> {
            if (producingCountryId != null) {
                Join<Object, Object> transportJoin = root.join("producingCountry");
                return transportJoin.get("id").in(producingCountryId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasWheelConfigurationId(List<Long> wheelConfigurationId) {
        return (root, query, cb) -> {
            if (wheelConfigurationId != null) {
                Join<Object, Object> transportJoin = root.join("wheelConfiguration");
                return transportJoin.get("id").in(wheelConfigurationId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> priceFrom(BigDecimal priceFrom) {
        return (root, query, cb) -> {
            if (priceFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), priceFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> priceTo(BigDecimal priceTo) {
        return (root, query, cb) -> {
            if (priceTo != null) {
                return cb.lessThanOrEqualTo(root.get("price"), priceTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }


    public static Specification<Transport> yearFrom(Integer yearsFrom) {
        return (root, query, cb) -> {
            if (yearsFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("year"), yearsFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> yearTo(Integer yearsTo) {
        return (root, query, cb) -> {
            if (yearsTo != null) {
                return cb.lessThanOrEqualTo(root.get("year"), yearsTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> mileageFrom(Integer mileageFrom) {
        return (root, query, cb) -> {
            if (mileageFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("mileage"), mileageFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> mileageTo(Integer mileageTo) {
        return (root, query, cb) -> {
            if (mileageTo != null) {
                return cb.lessThanOrEqualTo(root.get("mileage"), mileageTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> enginePowerFrom(Integer enginePowerFrom) {
        return (root, query, cb) -> {
            if (enginePowerFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("enginePower"), enginePowerFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> enginePowerTo(Integer enginePowerTo) {
        return (root, query, cb) -> {
            if (enginePowerTo != null) {
                return cb.lessThanOrEqualTo(root.get("enginePower"), enginePowerTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> engineDisplacementFrom(Double engineDisplacementFrom) {
        return (root, query, cb) -> {
            if (engineDisplacementFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("engineDisplacement"), engineDisplacementFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> engineDisplacementTo(Double engineDisplacementTo) {
        return (root, query, cb) -> {
            if (engineDisplacementTo != null) {
                return cb.lessThanOrEqualTo(root.get("engineDisplacement"), engineDisplacementTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> numberOfDoorsFrom(Integer numberOfDoorsFrom) {
        return (root, query, cb) -> {
            if (numberOfDoorsFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("numberOfDoors"), numberOfDoorsFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> numberOfDoorsTo(Integer numberOfDoorsTo) {
        return (root, query, cb) -> {
            if (numberOfDoorsTo != null) {
                return cb.lessThanOrEqualTo(root.get("numberOfDoors"), numberOfDoorsTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> numberOfSeatsFrom(Integer numberOfSeatsFrom) {
        return (root, query, cb) -> {
            if (numberOfSeatsFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("numberOfSeats"), numberOfSeatsFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> numberOfSeatsTo(Integer numberOfSeatsTo) {
        return (root, query, cb) -> {
            if (numberOfSeatsTo != null) {
                return cb.lessThanOrEqualTo(root.get("numberOfSeats"), numberOfSeatsTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }


    public static Specification<Transport> loadCapacityFrom(Integer loadCapacityFrom) {
        return (root, query, cb) -> {
            if (loadCapacityFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("loadCapacity"), loadCapacityFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> loadCapacityTo(Integer loadCapacityTo) {
        return (root, query, cb) -> {
            if (loadCapacityTo != null) {
                return cb.lessThanOrEqualTo(root.get("loadCapacity"), loadCapacityTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }


    public static Specification<Transport> hasTrade(Boolean trade) {
        return (root, query, cb) -> {
            if (trade != null) {
                return cb.equal(root.get("trade"), trade);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasMilitary(Boolean military) {
        return (root, query, cb) -> {
            if (military != null) {
                return cb.equal(root.get("military"), military);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasUncleared(Boolean uncleared) {
        return (root, query, cb) -> {
            if (uncleared != null) {
                return cb.equal(root.get("uncleared"), uncleared);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasBargain(Boolean bargain) {
        return (root, query, cb) -> {
            if (bargain != null) {
                return cb.equal(root.get("bargain"), bargain);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasInstallmentPayment(Boolean installmentPayment) {
        return (root, query, cb) -> {
            if (installmentPayment != null) {
                return cb.equal(root.get("installmentPayment"), installmentPayment);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> isActive() {
        return (root, query, cb) -> cb.equal(root.get("status"), Transport.Status.ACTIVE);
    }

    public static Specification<Transport> sortPopularTransports() {
        return (root, query, cb) -> {
            Join<Object, Object> transportViewsJoin = root.join("transportViews");
            query.groupBy(root.get("id"));
            Expression<Long> viewCount = cb.count(transportViewsJoin);
            query.orderBy(cb.desc(viewCount));
            return null;
        };
    }

    public static Specification<Transport> findTransportViewedByUser(Users user) {
        return (root, query, cb) -> {
            Join<Transport, TransportView> transportViewsJoin = root.join("transportViews");
            query.orderBy(cb.desc(transportViewsJoin.get("lastUpdate")));
            return cb.equal(transportViewsJoin.get("user"), user);
        };
    }

    public static Specification<Transport> findFavoriteTransportsByUser(Users user) {
        return (root, query, cb) -> {
            Join<Transport, TransportView> transportFavoritesJoin = root.join("transportFavorites");
            return cb.equal(transportFavoritesJoin.get("user"), user);
        };
    }

    public static Specification<Transport> findByUser(Users user) {
        return (root, query, cb) -> cb.equal(root.get("user"), user);
    }

    public static Specification<Transport> hasStatus(Transport.Status status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static <T> Specification<T> sortBy(Class<T> entityClass, SortBy sortBy, OrderBy orderBy) {
        return (root, query, cb) -> {
            if (sortBy != null && orderBy != null) {
                if (sortBy == SortBy.ASC) {
                    query.orderBy(cb.asc(root.get(orderBy.toString().toLowerCase())));
                } else if (sortBy == SortBy.DESC) {
                    query.orderBy(cb.desc(root.get(orderBy.toString().toLowerCase())));
                }
            }
            return cb.isTrue(cb.literal(true));
        };
    }
}
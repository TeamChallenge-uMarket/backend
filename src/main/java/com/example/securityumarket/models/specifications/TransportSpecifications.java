package com.example.securityumarket.models.specifications;

import com.example.securityumarket.models.entities.Transport;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class TransportSpecifications {

    public static Specification<Transport> hasTransportType(String transportType) {
        return (root, query, cb) -> {
            if (transportType != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                Join<Object, Object> typeBrandJoin = transportModelJoin.join("transportTypeBrand");
                Join<Object, Object> typeJoin = typeBrandJoin.join("transportType");
                return cb.equal(typeJoin.get("type"), transportType);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasTransportBrand(String transportBrand) {
        return (root, query, cb) -> {
            if (transportBrand != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                Join<Object, Object> typeBrandJoin = transportModelJoin.join("transportTypeBrand");
                Join<Object, Object> brandJoin = typeBrandJoin.join("transportBrand");
                return cb.equal(brandJoin.get("brand"), transportBrand);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasModelIn(List<String> models) {
        return (root, query, cb) -> {
            if (models != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                return transportModelJoin.get("model").in(models);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> yearFrom(Integer yearFrom) {
        return (root, query, cb) -> {
            if (yearFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("year"), yearFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> yearTo(Integer yearTo) {

        return (root, query, cb) -> {
            if (yearTo != null) {
                return cb.lessThanOrEqualTo(root.get("year"), yearTo);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasRegionIn(List<String> regions) {
        return (root, query, cb) -> {
            if (regions != null) {
                Join<Object, Object> transportCityJoin = root.join("city");
                Join<Object, Object> regionJoin = transportCityJoin.join("region");
                return regionJoin.get("description").in(regions);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasCityIn(List<String> city) {
        return (root, query, cb) -> {
            if (city != null) {
                Join<Object, Object> transportCityJoin = root.join("city");
                return transportCityJoin.get("description").in(city);
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

    public static Specification<Transport> hasBodyTypeIn(List<String> bodyTypes) {
        return (root, query, cb) -> {
            if (bodyTypes != null) {
                return root.get("bodyType").in(bodyTypes);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasFuelTypeIn(List<String> fuelTypes) {
        return (root, query, cb) -> {
            if (fuelTypes != null) {
                return root.get("fuelType").in(fuelTypes);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasTransmissionIn(List<String> transmissions) {
        return (root, query, cb) -> {
            if (transmissions != null) {
                return root.get("transmission").in(transmissions);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasColorIn(List<String> colors) {
        return (root, query, cb) -> {
            if (colors != null) {
                return root.get("color").in(colors);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasConditionIn(List<String> conditions) {
        return (root, query, cb) -> {
            if (conditions != null) {
                return root.get("condition").in(conditions);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasDriveType(List<String> driveTypes) {
        return (root, query, cb) -> {
            if (driveTypes != null) {
                return root.get("driveType").in(driveTypes);
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

    public static Specification<Transport> engineDisplacementFrom(Integer engineDisplacementFrom) {
        return (root, query, cb) -> {
            if (engineDisplacementFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("engineDisplacement"), engineDisplacementFrom);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> engineDisplacementTo(Integer engineDisplacementTo) {
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

    /// main page
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

    public static Specification<Transport> hasBrandId(Long brandId) {
        return (root, query, cb) -> {
            if (brandId != null) {
                Join<Object, Object> transportModelJoin = root.join("transportModel");
                Join<Object, Object> typeBrandJoin = transportModelJoin.join("transportTypeBrand");
                Join<Object, Object> typeJoin = typeBrandJoin.join("transportBrand");
                return cb.equal(typeJoin.get("id"), brandId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasModelId(List<Long> modelId) {
        return (root, query, cb) -> {
            if (modelId != null) {
                Join<Object, Object> typeJoin = root.join("transportModel");
                return typeJoin.get("id").in(modelId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<Transport> hasRegionId(List<Long> regionId) {
        return (root, query, cb) -> {
            if (regionId != null) {
                Join<Object, Object> transportModelJoin = root.join("city");
                Join<Object, Object> typeJoin = transportModelJoin.join("region");
                return typeJoin.get("id").in(regionId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }
}
package com.example.securityumarket.dao.specifications;

import com.example.securityumarket.models.City;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CitySpecifications {
    public static Specification<City> hasRegionId(List<Long> regionId) {
        return (root, query, cb) -> {
            if (regionId != null) {
                Join<Object, Object> cityJoin = root.join("region");
                return cityJoin.get("id").in(regionId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<City> hasRegionId(Long regionId) {
        return (root, query, cb) -> {
            if (regionId != null) {
                Join<Object, Object> cityJoin = root.join("region");
                return cb.equal(cityJoin.get("id"), regionId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }
}

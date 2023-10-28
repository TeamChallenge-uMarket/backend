package com.example.securityumarket.models.specifications;

import com.example.securityumarket.models.entities.City;
import com.example.securityumarket.models.entities.Transport;
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
}

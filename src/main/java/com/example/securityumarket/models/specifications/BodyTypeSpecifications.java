package com.example.securityumarket.models.specifications;

import com.example.securityumarket.models.entities.BodyType;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BodyTypeSpecifications {
    public static Specification<BodyType> hasTransportTypeId(Long transportTypeId) {
        return (root, query, cb) -> {
            if (transportTypeId != null) {
                Join<Object, Object> transportTypeJoin = root.join("transportType");
                return cb.equal(transportTypeJoin.get("id"), transportTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }
}

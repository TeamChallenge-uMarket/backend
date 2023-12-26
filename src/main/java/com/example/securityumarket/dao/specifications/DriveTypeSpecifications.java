package com.example.securityumarket.dao.specifications;

import com.example.securityumarket.models.DriveType;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class DriveTypeSpecifications {
    public static Specification<DriveType> hasTransportTypeId(Long transportTypeId) {
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

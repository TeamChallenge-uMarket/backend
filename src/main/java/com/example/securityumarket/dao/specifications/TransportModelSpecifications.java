package com.example.securityumarket.dao.specifications;

import com.example.securityumarket.models.TransportModel;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TransportModelSpecifications {
    public static Specification<TransportModel> hasTransportTypeId(Long transportTypeId) {
        return (root, query, cb) -> {
            if (transportTypeId != null) {
                Join<Object, Object> transportTypeBrandJoin = root.join("transportTypeBrand");
                Join<Object, Object> transportTypeJoin = transportTypeBrandJoin.join("transportType");
                return cb.equal(transportTypeJoin.get("id"), transportTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }

    public static Specification<TransportModel> hasTransportBrandId(List<Long> transportBrandId) {
        return (root, query, cb) -> {
            if (transportBrandId != null) {
                Join<Object, Object> transportTypeBrandJoin = root.join("transportTypeBrand");
                Join<Object, Object> transportBrandJoin = transportTypeBrandJoin.join("transportBrand");
                return transportBrandJoin.get("id").in(transportBrandId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }
}

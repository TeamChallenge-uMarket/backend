package com.example.securityumarket.dao.specifications;

import com.example.securityumarket.models.TransportBrand;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class TransportBrandSpecifications {

    public static Specification<TransportBrand> hasTransportTypeId(Long transportTypeId) {
        return (root, query, cb) -> {
            if (transportTypeId != null) {
                Join<Object, Object> transportTypeBrandsJoin = root.join("transportTypeBrands");
                Join<Object, Object> transportTypeJoin = transportTypeBrandsJoin.join("transportType");
                return cb.equal(transportTypeJoin.get("id"), transportTypeId);
            } else {
                return cb.isTrue(cb.literal(true));
            }
        };
    }
}

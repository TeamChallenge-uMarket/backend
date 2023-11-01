package com.example.securityumarket.models.specifications;

import com.example.securityumarket.models.entities.DriveType;
import com.example.securityumarket.models.entities.TransportBrand;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

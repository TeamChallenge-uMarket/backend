package com.example.securityumarket.models.entities;

import com.example.securityumarket.enums.SearchOrderBy;
import com.example.securityumarket.enums.SearchSortBy;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistory extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "transport_type_id")
    private Long transportTypeId;

    @Column(name = "brand_id", columnDefinition = "jsonb")
    private List<Long> brandId;

    @Column(name = "model_id", columnDefinition = "jsonb")
    private List<Long> modelId;

    @Column(name = "region_id", columnDefinition = "jsonb")
    private List<Long> regionId;

    @Column(name = "city_id", columnDefinition = "jsonb")
    private List<Long> cityId;

    @Column(name = "body_type_id", columnDefinition = "jsonb")
    private List<Long> bodyTypeId;

    @Column(name = "fuel_type_id", columnDefinition = "jsonb")
    private List<Long> fuelTypeId;

    @Column(name = "drive_type_id", columnDefinition = "jsonb")
    private List<Long> driveTypeId;

    @Column(name = "transmission_id", columnDefinition = "jsonb")
    private List<Long> transmissionId;

    @Column(name = "color_id", columnDefinition = "jsonb")
    private List<Long> colorId;

    @Column(name = "condition_id", columnDefinition = "jsonb")
    private List<Long> conditionId;

    @Column(name = "number_axles_id", columnDefinition = "jsonb")
    private List<Long> numberAxlesId;

    @Column(name = "producing_country_id", columnDefinition = "jsonb")
    private List<Long> producingCountryId;

    @Column(name = "wheel_configuration_id", columnDefinition = "jsonb")
    private List<Long> wheelConfigurationId;

    @OneToMany(mappedBy = "searchHistory")
    private List<UserSearchHistory> userSearchHistories;


    @Column(name = "price_from")
    private BigDecimal priceFrom;

    @Column(name = "price_to")
    private BigDecimal priceTo;

    @Column(name = "years_from")
    private Integer yearsFrom;

    @Column(name = "years_to")
    private Integer yearsTo;

    @Column(name = "mileage_from")
    private Integer mileageFrom;

    @Column(name = "mileage_to")
    private Integer mileageTo;

    @Column(name = "engine_power_from")
    private Integer enginePowerFrom;

    @Column(name = "engine_power_to")
    private Integer enginePowerTo;

    @Column(name = "engine_displacement_from")
    private Double engineDisplacementFrom;

    @Column(name = "engine_displacement_to")
    private Double engineDisplacementTo;

    @Column(name = "number_of_doors_from")
    private Integer numberOfDoorsFrom;

    @Column(name = "number_of_doors_to")
    private Integer numberOfDoorsTo;

    @Column(name = "number_of_seats_from")
    private Integer numberOfSeatsFrom;

    @Column(name = "number_of_seats_to")
    private Integer numberOfSeatsTo;

    @Column(name = "load_capacity_from")
    private Integer loadCapacityFrom;

    @Column(name = "load_capacity_to")
    private Integer loadCapacityTo;

    @Column(name = "trade")
    private Boolean trade;

    @Column(name = "military")
    private Boolean military;

    @Column(name = "uncleared")
    private Boolean uncleared;

    @Column(name = "bargain")
    private Boolean bargain;

    @Column(name = "installment_payment")
    private Boolean installmentPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_by")
    private SearchOrderBy orderBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "sort_by")
    private SearchSortBy sortBy;
}

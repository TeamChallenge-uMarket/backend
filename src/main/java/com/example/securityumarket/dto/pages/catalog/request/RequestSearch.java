    package com.example.securityumarket.dto.pages.catalog.request;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.io.Serializable;
    import java.math.BigDecimal;
    import java.util.List;


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public class RequestSearch implements Serializable {

        private Long transportTypeId;

        private List<Long> brandId;

        private List<Long> modelId;

        private List<Long> regionId;

        private List<Long> cityId;

        private List<Long> bodyTypeId;

        private List<Long> fuelTypeId;

        private List<Long> driveTypeId;

        private List<Long> transmissionId;

        private List<Long> colorId;

        private List<Long> conditionId;

        private List<Long> numberAxlesId;

        private List<Long> producingCountryId;

        private List<Long> wheelConfigurationId;

        private BigDecimal priceFrom;

        private BigDecimal priceTo;

        private Integer yearsFrom;

        private Integer yearsTo;

        private Integer mileageFrom;

        private Integer mileageTo;

        private Integer enginePowerFrom;

        private Integer enginePowerTo;

        private Double engineDisplacementFrom;

        private Double engineDisplacementTo;

        private Integer numberOfDoorsFrom;

        private Integer numberOfDoorsTo;

        private Integer numberOfSeatsFrom;

        private Integer numberOfSeatsTo;

        private Integer loadCapacityFrom;

        private Integer loadCapacityTo;

        private Boolean trade;

        private Boolean military;

        private Boolean uncleared;

        private Boolean bargain;

        private Boolean installmentPayment;

        private OrderBy orderBy;

        private SortBy sortBy;

        public enum SortBy {
            ASC, DESC
        }

        public enum OrderBy {
            CREATED, PRICE, MILEAGE
        }
    }
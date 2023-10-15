package com.example.securityumarket.dao;

import com.example.securityumarket.models.DTO.catalog.MotorcycleFilterDTO;
import com.example.securityumarket.models.DTO.main_page.request.RequestTransportSearchDTO;
import com.example.securityumarket.models.entities.Transport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransportDAO extends JpaRepository<Transport, Long> {

    @Query("select c from Transport c order by c.created desc")
    Optional<List<Transport>> findNewCars(PageRequest pageRequest);

    Optional<Transport> findById(long id);

    @Query("select t from Transport t " +
            "where (:#{#requestSearch.typeId} = 0 or t.transportModel.transportTypeBrand.transportType.id =:#{#requestSearch.typeId}) " +
            "and (:#{#requestSearch.brandId} = 0 or t.transportModel.transportTypeBrand.transportBrand.id =:#{#requestSearch.brandId}) " +
            "and (:#{#requestSearch.modelId} = 0 or t.transportModel.id =:#{#requestSearch.modelId}) " +
            "and (:#{#requestSearch.regionId} = 0 or t.city.region.id =:#{#requestSearch.regionId}) " +
            "order by " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'created' and lower(:#{#requestSearch.sortBy.name()}) = 'asc' then t.created end asc, " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'created' and lower(:#{#requestSearch.sortBy.name()}) = 'desc' then t.created end desc, " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'price' and  lower(:#{#requestSearch.sortBy.name()}) = 'asc' then t.price end asc, " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'price' and lower(:#{#requestSearch.sortBy.name()}) = 'desc' then t.price end desc")
    Optional<List<Transport>> findTransportByRequest(@Param("requestSearch") RequestTransportSearchDTO requestSearch, PageRequest pageRequest);

    @Query("select t from Transport t " +
            "where (:#{#motorcycleFilterDTO.transportType} = t.transportModel.transportTypeBrand.transportType.type) " +
            "and (t.price between :#{#motorcycleFilterDTO.priceFrom} and :#{#motorcycleFilterDTO.priceTo})" +
            "and (t.bodyType =:#{#motorcycleFilterDTO.bodyType}) " +
            "and (t.fuelType =:#{#motorcycleFilterDTO.fuelType}) " +
            "and (t.transmission =:#{#motorcycleFilterDTO.transmission}) " +
            "and (t.color =:#{#motorcycleFilterDTO.color}) " +
            "and (t.condition =:#{#motorcycleFilterDTO.condition}) " +
            "and (t.driveType =:#{#motorcycleFilterDTO.driveType}) " +
            "and (t.mileage between :#{#motorcycleFilterDTO.mileageFrom} and :#{#motorcycleFilterDTO.mileageTo}) " +
            "and (t.enginePower between :#{#motorcycleFilterDTO.enginePowerFrom} and :#{#motorcycleFilterDTO.enginePowerTo}) " +
            "order by " +
            "   case when lower(:#{#motorcycleFilterDTO.orderBy}) = 'created' and lower(:#{#motorcycleFilterDTO.sortBy}) = 'asc' then t.created end asc, " +
            "   case when lower(:#{#motorcycleFilterDTO.orderBy}) = 'created' and lower(:#{#motorcycleFilterDTO.sortBy}) = 'desc' then t.created end desc, " +
            "   case when lower(:#{#motorcycleFilterDTO.orderBy}) = 'price' and lower(:#{#motorcycleFilterDTO.sortBy}) = 'asc' then t.price end asc, " +
            "   case when lower(:#{#motorcycleFilterDTO.orderBy}) = 'price' and lower(:#{#motorcycleFilterDTO.sortBy}) = 'desc' then t.price end desc")
    List<Transport> findMotorCyclesByFilter(@Param("motorcycleFilterDTO") MotorcycleFilterDTO motorcycleFilterDTO, PageRequest pageRequest);
}

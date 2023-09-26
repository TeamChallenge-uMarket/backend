package com.example.securityumarket.dao;

import com.example.securityumarket.models.DTO.main_page.request.RequestCarSearchDTO;
import com.example.securityumarket.models.entities.Car;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarDAO extends JpaRepository<Car, Long> {

    @Query("select c from Car c order by c.created desc")
    List<Car> findNewCars(PageRequest pageRequest);

    @Query("select c from Car c " +
            "where (:#{#requestSearch.typeId} = 0 or c.carModel.carBrand.carType.id = :#{#requestSearch.typeId}) " +
            "and (:#{#requestSearch.brandId} = 0 or c.carModel.carBrand.id = :#{#requestSearch.brandId}) " +
            "and (:#{#requestSearch.modelId} = 0 or c.carModel.id = :#{#requestSearch.modelId}) " +
            "order by " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'created' and lower(:#{#requestSearch.sortBy.name()}) = 'asc' then c.created end asc, " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'created' and lower(:#{#requestSearch.sortBy.name()}) = 'desc' then c.created end desc, " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'price' and  lower(:#{#requestSearch.sortBy.name()}) = 'asc' then c.price.price end asc, " +
            "   case when lower(:#{#requestSearch.orderBy.name()}) = 'price' and lower(:#{#requestSearch.sortBy.name()}) = 'desc' then c.price.price end desc")
    List<Car> findCarsByRequest(@Param("requestSearch") RequestCarSearchDTO requestSearch, PageRequest pageRequest);
}

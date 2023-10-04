package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.CarPriceDAO;
import com.example.securityumarket.exception.DataNotValidException;
import com.example.securityumarket.models.entities.CarPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CarPriceService {
    private final CarPriceDAO carPriceDAO;

    public CarPrice save(BigDecimal price, boolean bargain, boolean trade, boolean military, boolean installmentPayment, boolean uncleared) {
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            CarPrice buildCarPrice = CarPrice.builder()
                    .price(price)
                    .bargain(bargain)
                    .trade(trade)
                    .military(military)
                    .installmentPayment(installmentPayment)
                    .uncleared(uncleared)
                    .build();

            return carPriceDAO.save(buildCarPrice);
        } else {
            throw new DataNotValidException("The price must be more than 0");
        }
    }
}

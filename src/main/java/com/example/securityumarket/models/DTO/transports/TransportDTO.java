package com.example.securityumarket.models.DTO.transports;

/*
id - унікальний ідентифікатор транспортного засобу.
bodyType - тип кузова транспортного засобу (седан, купе, SUV).
importedFrom - інформація про країну виробника.
year - рік випуску.
price - вартість транспортного засобу.
bargain - прапорець, що вказує на можливість торгу при продажі.
trade - прапорець, що вказує на можливість обміну.
military - прапорець, який вказує, на можливість торгу для військових.
installmentPayment - прапорець, що показує, чи можлива розстрочка платежу.
uncleared - прапорець, що вказує, чи транспортний засіб є розмитненим.
condition - стан транспортного засобу (наприклад, новий, б / у).
accidentHistory - прапорець, що вказує на наявність історії аварій.
vincode - VIN-код транспортного засобу.
description - опис транспортного засобу.
created - дата та час створення запису.
lastUpdate - дата та час останнього оновлення запису.
color - колір транспортного засобу.
regionId - ідентифікатор регіону, де розташований транспортний засіб.
cityId - ідентифікатор міста, де розташований транспортний засіб.
galleryId - ідентифікатор галереї фотографій транспортного засобу.
userId - ідентифікатор користувача, який додав інформацію про транспортний засіб.
modelId - ідентифікатор моделі транспортного засобу.
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class TransportDTO {

    protected long id;

    protected String bodyType;

    protected String importedFrom;

    protected int year;

    protected BigDecimal price;

    protected boolean bargain;

    protected boolean trade;

    protected boolean military;

    protected boolean installmentPayment;

    protected boolean uncleared;

    protected String condition;

    protected boolean accidentHistory;

    protected String vincode;

    protected String description;

    protected LocalDateTime created;

    protected LocalDateTime lastUpdate;

    protected String color;

    protected long regionId;

    protected long cityId;

    protected long galleryId;

    protected long userId;

    protected long modelId;
}

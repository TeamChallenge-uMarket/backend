--Passenger Cars
INSERT INTO transports (user_id, city, model_id, body_type, drive_type, fuel_type, producing_country, transmission,transport_color,
                        transport_condition, number_axles, wheel_configuration,year, mileage, vincode, description, engine_displacement,
                        engine_power, number_of_doors,number_of_seats, accident_history, fuel_consumption_city, fuel_consumption_highway,
                        fuel_consumption_mixed, load_capacity, price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update)
VALUES

    (2, 188, 1,  2, 2, 1, 3, 2, 1, 2, null, null,
     2018, 60000, 'ABC123XYZ456', 'description', 3.5, 140,
     5, 5, false, 12, 9, 10, null,
     14000, true, false, true, false, false, Now(), Now()),

    (2, 184, 2,2, 1, 1, 3, 2, 3, 1, null, null,
     2015, 60000, 'ABC123XYZ454', 'description', 2.5, 140,
     5, 5, false, 12, 9, 12, null,
     25000, true, false, true, false, false, Now(), Now()),

    (2, 135, 145,4, 1, 2, 5, 2, 1, 1, null, null,
     2012, 130000, 'ABC123XYZ054', 'description', 3.0, 245,
     5, 7, false, 15, 9, 11, null,
     22000, true, false, true, false, false, Now(), Now()),

    (2, 136, 128,1, 3, 1, 5, 1, 7, 2, null, null,
     2014, 160000, 'ABC433XYZ054', 'description', 2.0, 180,
     5, 5, false, 10, 9, 9.5, null,
     13000, false, false, false, false, false, Now(), Now()),

    (1, 141, 175,1, 1, 7, 5, 4, 1, 1, null, null,
     2018, 210000, 'ABC433XYZ104', 'description', 3.0, 195,
     5, 5, false, 10, 5, 6.5, null,
     35000, false, false, false, true, false, Now(), Now()),

    (1, 1, 175,2, 2, 1, 5, 1, 1, 2, null, null,
     2012, 350000, 'ABC433XYZ105', 'description', 2.0, 140,
     5, 5, true, 10, 5, 6.5, null,
     12000, false, false, false, true, false, Now(), Now()),

    (2, 20, 453,2, 2, 8, 2, 2, 3, 2, null, null,
     2014, 200000, 'ABC433XYZ205', 'description', null, 356,
     5, 5, true, null, null, null, null,
     24000, true, true, true, false, false, Now(), Now()),

    (2, 25, 66,4, 2, 1, 3, 2, 4, 1, null, null,
     2015, 240000, 'ABC097XYZ205', 'description', 2.0, 210,
     5, 5, false, 12, 7, 10, null,
     19000, true, true, true, false, false, Now(), Now()),

    (1, 29, 27,3, 2, 3, 2, 1, 10, 2, null, null,
     2008, 310000, 'ABC400XYZ205', 'description', 1.6, 105,
     5, 5, true, 8, 7, 7.5, null,
     5600, true, true, true, false, false, Now(), Now());


-- Moto
INSERT INTO transports (user_id, city, model_id, body_type, drive_type, fuel_type, producing_country, transmission,transport_color,
                        transport_condition,year, mileage, vincode, description, engine_displacement,
                        engine_power,number_of_seats, accident_history,fuel_consumption_mixed, price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update)
VALUES
        (1, 1, 704, 22, 6, 1, 5, 4, 1, 1, 2015,
        34000, 'ABC400XYZ999', 'description moto', 1, 210, 2, false, 10,
        2400,true, true, true, false, false, Now(), Now()),

        (2, 2, 716, 23, 5, 1, 5, 4, 1, 1, 2016,
         30000, 'ABC401XYZ999', 'description moto', 1, 230, 2, false, 10,
         3100,true, true, false, false, false, Now(), Now()),

        (2, 25, 726, 23, 6, 1, 5, 4, 1, 1, 2019,
         15000, 'ABC400XYZ919', 'description moto', 1, 297, 2, false, 10,
         6900,true, true, true, false, false, Now(), Now());


--Trucks
INSERT INTO transports (user_id, city, model_id, body_type, drive_type, fuel_type, producing_country, transmission,transport_color,
                        transport_condition, number_axles, wheel_configuration,year, mileage, vincode, description, engine_displacement,
                        engine_power, number_of_doors,number_of_seats, accident_history, fuel_consumption_city, fuel_consumption_highway,
                        fuel_consumption_mixed, load_capacity, price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update)
VALUES

    (2, 188, 989,  25, 2, 8, 2, 2, 3, 1, 4, 2,
     2021, 30000, 'ABC123XYZ156', 'description', null, 740,
     2, 3, false, null, null, null, 3000,
     120000, false, false, false, false, true, Now(), Now()),

    (2, 184, 990,  26, 2, 1, 5, 1, 1, 2, 4, 1,
     2015, 180000, 'ABC123XYZ050', 'description', 2.0, 189,
     7, 9, false, null, null, null, 3000,
     24000, false, false, false, false, true, Now(), Now());

--Spec
INSERT INTO transports (user_id, city, model_id, body_type, drive_type, fuel_type, producing_country, transmission,transport_color,
                        transport_condition, number_axles, wheel_configuration,year, mileage, vincode, description, engine_displacement,
                        engine_power, number_of_doors,number_of_seats, accident_history, fuel_consumption_city, fuel_consumption_highway,
                        fuel_consumption_mixed, load_capacity, price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update)
VALUES

    (1, 135, 976,  33, 1, 7, 29, 1, 6, 1, 4, 2,
     2001, 24000, 'ABC021XYZ156', 'description', 5.0, 120,
     2, 2, false, null, null, null, 3000,
     8000, false, false, false, false, true, Now(), Now()),

    (1, 135, 975,  30, 1, 7, 5, null, 6, 1, null, null,
     2005, null, 'ABC925XYZ156', 'description', null, null,
     2, 2, false, null, null, null, 20000,
     30000, false, false, false, false, true, Now(), Now());

--Agr
INSERT INTO transports (user_id, city, model_id, body_type, drive_type, fuel_type, producing_country, transmission,transport_color,
                        transport_condition, number_axles, wheel_configuration,year, mileage, vincode, description, engine_displacement,
                        engine_power, number_of_doors,number_of_seats, accident_history, fuel_consumption_city, fuel_consumption_highway,
                        fuel_consumption_mixed, load_capacity, price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update)
VALUES

    (1, 135, 1069,  43, 1, 7, 29, 1, 10, 1, null, null,
     1985, null, 'ABC020XYZ106', 'description', null, null,
     2, 2, false, null, null, null, 3000,
     2900, false, false, false, false, true, Now(), Now()),

    (1, 135, 1067,  45, null, 7, null, 1, 10, 1, null, null,
     2002, null, 'ABC120XYZ106', 'description', null, 120,
     2, 3, false, null, null, null, null,
     12000, false, false, false, false, true, Now(), Now());

--Water
INSERT INTO transports (user_id, city, model_id, body_type, fuel_type, producing_country, transport_color,
                        transport_condition, year, mileage, vincode, description, engine_displacement,
                        engine_power,number_of_seats, accident_history,
                        price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update)
VALUES

    (1, 135, 1054,  48,  7, 2,  3, 1,
     2012, null, 'ABC0524XYZ106', 'description', null, null,
     9, false,
     74000, false, false, false, false, true, Now(), Now()),

    (1, 135, 1055,  49,  7, 2,  3, 1,
     2014, null, 'ABC0524XYZ106', 'description', null, null,
     5, false,
     50000, false, false, false, false, true, Now(), Now());
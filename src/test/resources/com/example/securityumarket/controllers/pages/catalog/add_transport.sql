INSERT INTO transports (model_id, body_type, drive_type, fuel_type, producing_country, transmission,transport_color,
                        transport_condition,year, mileage, vincode, description, engine_displacement,
                        engine_power, number_of_doors,number_of_seats, accident_history, fuel_consumption_city, fuel_consumption_highway,
                        fuel_consumption_mixed, load_capacity, price, bargain, trade, military, installment_payment,
                        uncleared, created, last_update, status)
VALUES

    (1,  2, 2, 1, 3, 2, 1, 2,
     2018, 60000, 'ABC123XYZ000', 'test-transport', 3.5, 140,
     5, 5, false, 12, 9, 10, null,
     14000, true, false, true, false, false, Now(), Now(), 'ACTIVE');
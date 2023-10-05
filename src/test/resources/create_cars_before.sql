DELETE
FROM "uAutoTest".public.cars;

INSERT INTO "uAutoTest".public.cars (accident_history, body_type, color, condition, description, drive_type, engine_displacement,
                  fuel_consumption_city, fuel_consumption_highway, fuel_consumption_mixed,
                  engine_power, fuel_type, imported_from, mileage, number_of_doors, number_of_seats, transmission,
                  vincode, year, bargain, installment_payment, military, price, trade, uncleared, model_id, city,
                  user_id, created, last_update)
VALUES (
--      first car
           false, -- accident_history (boolean)
           'Sedan', -- body_type
           'Blue', -- color
           'Used', -- condition
           'A reliable car in good condition.', -- description
           'Front-wheel drive', -- drive_type
           2.0, -- engine_displacement
           9.5, -- fuel_consumption_city
           6.4, -- fuel_consumption_highway
           8.1, -- fuel_consumption_mixed
           150, -- engine_power
           'Petrol', -- fuel_type
           'Germany', -- imported_from
           80000, -- mileage
           4, -- number_of_doors
           5, -- number_of_seats
           'Automatic', -- transmission
           'ABC123XYZ456', -- vincode
           2018, -- year
           true,
           true,
           true,
           9500,
           true,
           false,
           43, -- model_id (замініть це на ID вашої моделі)
           9, -- city id
           2, -- user_id (замініть це на ID користувача, який додав цей запис)
           NOW(), -- created (поточна дата і час)
           NOW() -- last_update (поточна дата і час)
       ),
--     second car
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable car in good condition.', -- description
        'Front-wheel drive', -- drive_type
        2.5, -- engine_displacement
        12, -- fuel_consumption_city
        8.4, -- fuel_consumption_highway
        10, -- fuel_consumption_mixed
        150, -- engine_power
        'Petrol', -- fuel_type
        'Germany', -- imported_from
        85000, -- mileage
        4, -- number_of_doors
        5, -- number_of_seats
        'Automatic', -- transmission
        'ABC124XYZ457', -- vincode
        2014, -- year
        true,
        true,
        true,
        12000,
        true,
        true,
        4, -- model_id (замініть це на ID вашої моделі)
        4, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
--      third car
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable car in good condition.', -- description
        'Front-wheel drive', -- drive_type
        2.5, -- engine_displacement
        12, -- fuel_consumption_city
        8.4, -- fuel_consumption_highway
        10, -- fuel_consumption_mixed
        250, -- engine_power
        'Petrol', -- fuel_type
        'Germany', -- imported_from
        10000, -- mileage
        4, -- number_of_doors
        5, -- number_of_seats
        'Automatic', -- transmission
        'ABC124XYZ427', -- vincode
        2022, -- year
        false,
        false,
        false,
        35000,
        false,
        false,
        10, -- model_id (замініть це на ID вашої моделі)
        5, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
--      forth car
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable car in good condition.', -- description
        'Front-wheel drive', -- drive_type
        2.5, -- engine_displacement
        9, -- fuel_consumption_city
        5.4, -- fuel_consumption_highway
        6, -- fuel_consumption_mixed
        124, -- engine_power
        'Petrol', -- fuel_type
        'Germany', -- imported_from
        144000, -- mileage
        5, -- number_of_doors
        5, -- number_of_seats
        'Automatic', -- transmission
        'ABC144XYZ417', -- vincode
        2010, -- year
        true,
        true,
        true,
        6000,
        true,
        true,
        18, -- model_id (замініть це на ID вашої моделі)
        8, -- city id
        1, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
       --     five car
       (true, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable car in good condition.', -- description
        'Front-wheel drive', -- drive_type
        2.0, -- engine_displacement
        11, -- fuel_consumption_city
        9.4, -- fuel_consumption_highway
        10, -- fuel_consumption_mixed
        310, -- engine_power
        'Petrol', -- fuel_type
        'Germany', -- imported_from
        10000, -- mileage
        4, -- number_of_doors
        5, -- number_of_seats
        'Automatic', -- transmission
        'ABC104XYZ457', -- vincode
        2018, -- year
        true,
        true,
        true,
        14000,
        true,
        true,
        10, -- model_id (замініть це на ID вашої моделі)
        1, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
       --     six car
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable car in good condition.', -- description
        'Front-wheel drive', -- drive_type
        2.5, -- engine_displacement
        12, -- fuel_consumption_city
        8.4, -- fuel_consumption_highway
        10, -- fuel_consumption_mixed
        199, -- engine_power
        'Petrol', -- fuel_type
        'Germany', -- imported_from
        65000, -- mileage
        4, -- number_of_doors
        5, -- number_of_seats
        'Automatic', -- transmission
        'ABC124XYZ057', -- vincode
        2017, -- year
        true,
        true,
        true,
        15000,
        true,
        true,
        12, -- model_id (замініть це на ID вашої моделі)
        10, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       );
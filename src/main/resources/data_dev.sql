INSERT INTO type_cars (type)
VALUES ('легкові'),
       ('мототранспорт'),
       ('електротранспорт'),
       ('причепи'),
       ('вантажівки'),
       ('водний транспорт');

INSERT INTO brand_cars (brand, type_id)
VALUES ('Toyota', 1),
       ('Honda', 1),
       ('Ford', 1),
       ('Chevrolet', 1),
       ('Nissan', 1),
       ('BMW', 1),
       ('Mercedes-Benz', 1),
       ('Audi', 1),
       ('Volkswagen', 1),
       ('Hyundai', 1),
       ('Kia', 1),
       ('Subaru', 1),
       ('Mazda', 1),
       ('Lexus', 1),
       ('Acura', 1);

-- Models for Toyota
INSERT INTO models (model, brand_id)
VALUES ('Corolla', 1),
       ('Camry', 1),
       ('RAV4', 1),
       ('Prius', 1),
       ('Highlander', 1),
       ('Sienna', 1);

-- Models for Honda
INSERT INTO models (model, brand_id)
VALUES ('Civic', 2),
       ('Accord', 2),
       ('CR-V', 2),
       ('Fit', 2),
       ('HR-V', 2);

-- Models for Ford
INSERT INTO models (model, brand_id)
VALUES ('F-150', 3),
       ('Focus', 3),
       ('Escape', 3),
       ('Explorer', 3),
       ('Mustang', 3);

-- Models for Chevrolet
INSERT INTO models (model, brand_id)
VALUES ('Silverado', 4),
       ('Malibu', 4),
       ('Equinox', 4),
       ('Cruze', 4),
       ('Traverse', 4);

-- Models for Nissan
INSERT INTO models (model, brand_id)
VALUES ('Altima', 5),
       ('Maxima', 5),
       ('Rogue', 5),
       ('Sentra', 5),
       ('Pathfinder', 5);

-- Models for BMW
INSERT INTO models (model, brand_id)
VALUES ('3 Series', 6),
       ('5 Series', 6),
       ('X3', 6),
       ('X5', 6),
       ('7 Series', 6);

-- Models for Mercedes-Benz
INSERT INTO models (model, brand_id)
VALUES ('C-Class', 7),
       ('E-Class', 7),
       ('GLC', 7),
       ('GLE', 7),
       ('S-Class', 7);

-- Models for Audi
INSERT INTO models (model, brand_id)
VALUES ('A3', 8),
       ('A4', 8),
       ('Q5', 8),
       ('Q7', 8),
       ('A8', 8);

-- Models for Volkswagen
INSERT INTO models (model, brand_id)
VALUES ('Jetta', 9),
       ('Passat', 9),
       ('Tiguan', 9),
       ('Atlas', 9),
       ('Golf', 9);

-- Models for Hyundai
INSERT INTO models (model, brand_id)
VALUES ('Elantra', 10),
       ('Sonata', 10),
       ('Tucson', 10),
       ('Santa Fe', 10),
       ('Kona', 10);

-- Models for Kia
INSERT INTO models (model, brand_id)
VALUES ('Forte', 11),
       ('Optima', 11),
       ('Sportage', 11),
       ('Sorento', 11),
       ('Soul', 11);

-- Models for Subaru
INSERT INTO models (model, brand_id)
VALUES ('Impreza', 12),
       ('Legacy', 12),
       ('Outback', 12),
       ('Forester', 12),
       ('Crosstrek', 12);

-- Models for Mazda
INSERT INTO models (model, brand_id)
VALUES ('Mazda3', 13),
       ('Mazda6', 13),
       ('CX-5', 13),
       ('MX-5 Miata', 13),
       ('CX-9', 13);

-- Models for Lexus
INSERT INTO models (model, brand_id)
VALUES ('IS', 14),
       ('ES', 14),
       ('RX', 14),
       ('NX', 14),
       ('GX', 14);

-- Models for Acura
INSERT INTO models (model, brand_id)
VALUES ('ILX', 15),
       ('TLX', 15),
       ('MDX', 15),
       ('RDX', 15),
       ('NSX', 15);


INSERT INTO regions (description)
VALUES ('Вінницька область'),
       ('Волинська область'),
       ('Дніпропетровська область'),
       ('Донецька область'),
       ('Житомирська область'),
       ('Закарпатська область'),
       ('Запорізька область'),
       ('Івано-Франківська область'),
       ('Київська область'),
       ('Кіровоградська область'),
       ('Луганська область'),
       ('Львівська область'),
       ('Миколаївська область'),
       ('Одеська область'),
       ('Полтавська область'),
       ('Рівненська область'),
       ('Сумська область'),
       ('Тернопільська область'),
       ('Харківська область'),
       ('Херсонська область'),
       ('Хмельницька область'),
       ('Черкаська область'),
       ('Чернівецька область'),
       ('Чернігівська область');

INSERT INTO cities (description, region_id)
VALUES ('Вінниця', 1),
       ('Луцьк', 2),
       ('Дніпро', 3),
       ('Донецьк', 4),
       ('Житомир', 5),
       ('Ужгород', 6),
       ('Запоріжжя', 7),
       ('Івано-Франківськ', 8),
       ('Київ', 9),
       ('Кропивницький', 10),
       ('Луганськ', 11),
       ('Львів', 12),
       ('Миколаїв', 13),
       ('Одеса', 14),
       ('Полтава', 15),
       ('Рівне', 16),
       ('Суми', 17),
       ('Тернопіль', 18),
       ('Харків', 19),
       ('Херсон', 20),
       ('Хмельницький', 21),
       ('Черкаси', 22),
       ('Чернівці', 23),
       ('Чернігів', 24);

INSERT INTO fuel_consumption (city, highway, mixed)
VALUES (10.1, 7.2, 8.7),
       (7.1, 4.2, 5.7),
       (13.1, 8.8, 10.7),
       (4.9, 3.7, 4.1),
       (5.9, 3.7, 4.1),
       (4.9, 3.7, 4.1),
       (3.9, 3.7, 4.1),
       (5.9, 3.7, 4.1),
       (4.9, 3.7, 4.1),
       (4.9, 3.7, 4.1),
       (4.9, 3.7, 4.1);

-- PRICES
INSERT INTO car_prices (bargain, installment_payment, military, price, trade, uncleared)
VALUES (true, false, false, 20000, true, false),
       (true, true, false, 10000, true, false),
       (true, true, false, 1000, true, false),
       (true, false, false, 9000, false, false),
       (false, false, false, 23600, false, false),
       (true, false, false, 11000, false, false),
       (true, false, false, 15000, false, false),
       (true, false, false, 14000, false, false),
       (true, false, false, 13000, false, false),
       (true, false, false, 19000, false, false),
       (false, true, true, 25000, false, true);


INSERT INTO cars (accident_history, body_type, color, condition, description, drive_type, engine_displacement,
                  engine_power, fuel_type, imported_from, mileage, number_of_doors, number_of_seats, transmission,
                  vincode, year, model_id, city, fuel_consumption_id, price_id, user_id, created, last_update)
VALUES (
--      first car
           false, -- accident_history (boolean)
           'Sedan', -- body_type
           'Blue', -- color
           'Used', -- condition
           'A reliable car in good condition.', -- description
           'Front-wheel drive', -- drive_type
           2.0, -- engine_displacement
           150, -- engine_power
           'Petrol', -- fuel_type
           'Germany', -- imported_from
           80000, -- mileage
           4, -- number_of_doors
           5, -- number_of_seats
           'Automatic', -- transmission
           'ABC123XYZ456', -- vincode
           2018, -- year
           43, -- model_id (замініть це на ID вашої моделі)
           9, -- city id
           1, -- fuel_consumption_id (замініть це на ID вашого показника витрат пального)
           1, -- price_id (замініть це на ID вашої ціни)
           1, -- user_id (замініть це на ID користувача, який додав цей запис)
           NOW(), -- created (поточна дата і час)
           NOW() -- last_update (поточна дата і час)
       ),
--     second car
       (true, -- accident_history (boolean)
        'SUV', -- body_type
        'Red', -- color
        'Used', -- condition
        'Good family car with spacious interior.', -- description
        'All-wheel drive', -- drive_type
        2.5, -- engine_displacement
        180, -- engine_power
        'Diesel', -- fuel_type
        'USA', -- imported_from
        60000, -- mileage
        5, -- number_of_doors
        7, -- number_of_seats
        'Manual', -- transmission
        'DEF456UVW789', -- vincode
        2017, -- year
        64, -- model_id (замініть це на ID іншої моделі)
        12, -- city id
        2, -- fuel_consumption_id (замініть це на ID іншого показника витрат пального)
        2, -- price_id (замініть це на ID іншої ціни)
        1, -- user_id (замініть це на ID іншого користувача)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
--      third car
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'black', -- color
        'New', -- condition
        'A good condition.', -- description
        'Front-wheel drive', -- drive_type
        2.0, -- engine_displacement
        110, -- engine_power
        'Petrol', -- fuel_type
        'Norway', -- imported_from
        800000, -- mileage
        3, -- number_of_doors
        4, -- number_of_seats
        'Automatic', -- transmission
        'ABC123X456C', -- vincode
        2077, -- year
        33, -- model_id (замініть це на ID вашої моделі)
        3, -- city id
        3, -- fuel_consumption_id (замініть це на ID вашого показника витрат пального)
        3, -- price_id (замініть це на ID вашої ціни)
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
--      forth car
       (false, -- accident_history (boolean)
        'Hatch', -- body_type
        'White', -- color
        'Used', -- condition
        'Not good condition.', -- description
        'Front-wheel drive', -- drive_type
        1.0, -- engine_displacement
        110, -- engine_power
        'Gasoline', -- fuel_type
        'Hungry', -- imported_from
        8000, -- mileage
        5, -- number_of_doors
        5, -- number_of_seats
        'Automatic', -- transmission
        'ABC12113X456C', -- vincode
        1990, -- year
        33, -- model_id (замініть це на ID вашої моделі)
        3, -- city id
        4, -- fuel_consumption_id (замініть це на ID вашого показника витрат пального)
        4, -- price_id (замініть це на ID вашої ціни)
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
       --     five car
       (true, -- accident_history (boolean)
        'SUV', -- body_type
        'Green', -- color
        'Used', -- condition
        'Wife''s car', -- description
        'All-wheel drive', -- drive_type
        2.3, -- engine_displacement
        170, -- engine_power
        'Diesel', -- fuel_type
        'USA', -- imported_from
        6000, -- mileage
        5, -- number_of_doors
        7, -- number_of_seats
        'Automatic', -- transmission
        'DEF456UVW789', -- vincode
        2017, -- year
        53, -- model_id (замініть це на ID іншої моделі)
        12, -- city id
        5, -- fuel_consumption_id (замініть це на ID іншого показника витрат пального)
        5, -- price_id (замініть це на ID іншої ціни)
        2, -- user_id (замініть це на ID іншого користувача)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
       --     six car
       (true, -- accident_history (boolean)
        'SUV', -- body_type
        'Green', -- color
        'Used', -- condition
        'Wife''s car', -- description
        'All-wheel drive', -- drive_type
        2.3, -- engine_displacement
        170, -- engine_power
        'Diesel', -- fuel_type
        'USA', -- imported_from
        6000, -- mileage
        5, -- number_of_doors
        7, -- number_of_seats
        'Automatic', -- transmission
        'DEF456UVW789', -- vincode
        2017, -- year
        53, -- model_id (замініть це на ID іншої моделі)
        12, -- city id
        6, -- fuel_consumption_id (замініть це на ID іншого показника витрат пального)
        6, -- price_id (замініть це на ID іншої ціни)
        2, -- user_id (замініть це на ID іншого користувача)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),

-- Row 2
       (false,
        'Sedan',
        'Blue',
        'New',
        'First owner',
        'Front-wheel drive',
        1.6,
        120,
        'Petrol',
        'Germany',
        15000,
        4,
        5,
        'Manual',
        'ABC123XYZ456',
        2022,
        45,
        8,
        7,
        7,
        1,
        NOW(),
        NOW()),
-- Row 3
       (true,
        'Convertible',
        'Red',
        'Used',
        'Fun to drive',
        'Rear-wheel drive',
        3,
        200,
        'Petrol',
        'Italy',
        8000,
        2,
        2,
        'Automatic',
        'XYZ789ABC123',
        2020,
        61,
        13,
        8,
        8,
        1,
        NOW(),
        NOW()),
-- Row 4
       (false,
        'Hatchback',
        'Silver',
        'Used',
        'Great daily driver',
        'Front-wheel drive',
        1.4,
        100,
        'Petrol',
        'France',
        30000,
        5,
        5,
        'Automatic',
        'LMN456OPQ789',
        2019,
        18,
        20,
        9,
        9,
        2,
        NOW(),
        NOW()),
-- Row 5
       (true,
        'SUV',
        'Black',
        'Used',
        'Family car',
        'All-wheel drive',
        2.5,
        180,
        'Petrol',
        'USA',
        25000,
        4,
        5,
        'Automatic',
        'PQR789XYZ123',
        2021,
        19,
        10,
        10,
        10,
        1,
        NOW(),
        NOW()),
-- Row 6
       (false,
        'Sedan',
        'White',
        'New',
        'Low mileage',
        'Front-wheel drive',
        1.8,
        150,
        'Petrol',
        'Germany',
        10000,
        4,
        5,
        'Automatic',
        'JKL123MNO456',
        2023,
        29,
        16,
        11,
        11,
        2,
        NOW(),
        NOW());




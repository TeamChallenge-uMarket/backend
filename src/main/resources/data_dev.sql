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

INSERT INTO users (name, email, password, phone, refresh_token, city, created, last_update)
VALUES (
           'Імя', -- Замініть на реальне ім'я користувача
           'email@example.com', -- Замініть на реальний email користувача
           'password11', -- Замініть на хешований пароль користувача
           '+380980000000', -- Замініть на реальний номер телефону користувача
           'refresh_token_value', -- Замініть на реальний refresh token користувача
           1, -- Замініть на ID міста, в якому проживає користувач
           TO_TIMESTAMP('2023-08-26 20:31:23', 'YYYY-MM-DD HH24:MI:SS'), -- created (поточна дата і час)
           NOW() -- last_update (поточна дата і час)
       );

INSERT INTO users(id, created, last_update,
                  email, name, password, phone,
                  refresh_token, city)
VALUES (2, '2023-09-27 01:02:29.892836', '2023-10-03 17:37:59.276989',
        'praice07@gmail.com', 'Oleh11',
        'Password11', '+380982704401',
        'refresh_token_value',
        12);



INSERT INTO cars (accident_history, body_type, color, condition, description, drive_type, engine_displacement,
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

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN'),
       ('MODERATOR'),
       ('GUEST');

INSERT INTO permissions (description)
VALUES ('Permission 1 Description'),
       ('Permission 2 Description'),
       ('Permission 3 Description');

INSERT INTO user_has_roles (user_id, role_id)
VALUES (1, 1), -- User1
       (2, 1), -- User2
       (2, 2);

INSERT INTO user_has_permissions (user_id, permissions_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3);

INSERT INTO role_has_permissions (role_id, permissions_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (4, 1);



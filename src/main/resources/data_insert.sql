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

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN'),
       ('MODERATOR'),
       ('GUEST');

INSERT INTO permissions (description)
VALUES ('Permission 1 Description'),
       ('Permission 2 Description'),
       ('Permission 3 Description');

INSERT INTO role_has_permissions (role_id, permissions_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (4, 1);

INSERT INTO users (name, email, password, phone, refresh_token, city, created, last_update, active)
VALUES ('User', -- Замініть на реальне ім'я користувача
        'email@example.com', -- Замініть на реальний email користувача
        '$2a$10$cqJUGo38JPgXeaU4WZk5FuNKnAquHLdgrG7qpmbT7L2rCIZyyVAT2', -- Замініть на хешований пароль користувача
        '+380980000000', -- Замініть на реальний номер телефону користувача
        'refresh_token_value', -- Замініть на реальний refresh token користувача
        1, -- Замініть на ID міста, в якому проживає користувач
        TO_TIMESTAMP('2023-08-26 20:31:23', 'YYYY-MM-DD HH24:MI:SS'), -- created (поточна дата і час)
        NOW(),
        true);

INSERT INTO users(id, created, last_update,
                  email, name, password, phone,
                  refresh_token, city, active)
VALUES (2, '2023-09-27 01:02:29.892836', '2023-10-03 17:37:59.276989',
        'praice07@gmail.com', 'Oleh11',
        '$2a$10$gfgT9Ym4m8aR1kSYPrMrLexINYPXwis0.IxNry6vFQvZ/eBbZBN2O', '+380982704401',
        'refresh_token_value',
        12,
        true);

INSERT INTO user_has_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);

INSERT INTO user_has_permissions (user_id, permissions_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3);

INSERT INTO transport_types (type)
VALUES ('легкові'),
       ('мототранспорт'),
       ('електротранспорт'),
       ('причепи'),
       ('вантажівки'),
       ('водний транспорт');


INSERT INTO transport_brands (brand)
VALUES ('Toyota'),
       ('Honda'),
       ('Ford'),
       ('Chevrolet'),
       ('Nissan'),
       ('BMW'),
       ('Mercedes-Benz'),
       ('Audi'),
       ('Volkswagen'),
       ('Hyundai'),
       ('Kia'),
       ('Subaru'),
       ('Mazda'),
       ('Lexus'),
       ('Acura');

INSERT INTO transport_type_brands (type_id, brand_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 1);

-- Models for Toyota
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Corolla', 1),
       ('Camry', 1),
       ('RAV4', 1),
       ('Prius', 1),
       ('modelMoto1', 16),
       ('modelMoto2', 16),
       ('modelMoto3', 16);

-- Models for Honda
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Civic', 2),
       ('Accord', 2),
       ('CR-V', 2),
       ('Fit', 2),
       ('HondaMoto1', 17),
       ('HR-V', 2);

-- Models for Ford
INSERT INTO transport_models (model, type_brand_id)
VALUES ('F-150', 3),
       ('Focus', 3),
       ('Escape', 3),
       ('Explorer', 3),
       ('Mustang', 3);

-- Models for Chevrolet
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Silverado', 4),
       ('Malibu', 4),
       ('Equinox', 4),
       ('Cruze', 4),
       ('Traverse', 4);

-- Models for Nissan
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Altima', 5),
       ('Maxima', 5),
       ('Rogue', 5),
       ('Sentra', 5),
       ('Pathfinder', 5);

-- Models for BMW
INSERT INTO transport_models (model, type_brand_id)
VALUES ('3 Series', 6),
       ('5 Series', 6),
       ('X3', 6),
       ('X5', 6),
       ('7 Series', 6);

-- Models for Mercedes-Benz
INSERT INTO transport_models (model, type_brand_id)
VALUES ('C-Class', 7),
       ('E-Class', 7),
       ('GLC', 7),
       ('GLE', 7),
       ('S-Class', 7);

-- Models for Audi
INSERT INTO transport_models (model, type_brand_id)
VALUES ('A3', 8),
       ('A4', 8),
       ('Q5', 8),
       ('Q7', 8),
       ('A8', 8);

-- Models for Volkswagen
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Jetta', 9),
       ('Passat', 9),
       ('Tiguan', 9),
       ('Atlas', 9),
       ('Golf', 9);

-- Models for Hyundai
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Elantra', 10),
       ('Sonata', 10),
       ('Tucson', 10),
       ('Santa Fe', 10),
       ('Kona', 10);

-- Models for Kia
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Forte', 11),
       ('Optima', 11),
       ('Sportage', 11),
       ('Sorento', 11),
       ('Soul', 11);

-- Models for Subaru
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Impreza', 12),
       ('Legacy', 12),
       ('Outback', 12),
       ('Forester', 12),
       ('Crosstrek', 12);

-- Models for Mazda
INSERT INTO transport_models (model, type_brand_id)
VALUES ('Mazda3', 13),
       ('Mazda6', 13),
       ('CX-5', 13),
       ('MX-5 Miata', 13),
       ('CX-9', 13);

-- Models for Lexus
INSERT INTO transport_models (model, type_brand_id)
VALUES ('IS', 14),
       ('ES', 14),
       ('RX', 14),
       ('NX', 14),
       ('GX', 14);

-- Models for Acura
INSERT INTO transport_models (model, type_brand_id)
VALUES ('ILX', 15),
       ('TLX', 15),
       ('MDX', 15),
       ('RDX', 15),
       ('NSX', 15);

INSERT INTO transports (accident_history, body_type, color, condition, description, drive_type, engine_displacement,
                        fuel_consumption_city, fuel_consumption_highway, fuel_consumption_mixed,
                        engine_power, fuel_type, imported_from, mileage, number_of_doors, number_of_seats, transmission,
                        vincode, year, bargain, installment_payment, military, price, trade, uncleared, model_id, city,
                        user_id, created, last_update)
VALUES (
--      first transport
           false, -- accident_history (boolean)
           'Sedan', -- body_type
           'Blue', -- color
           'Used', -- condition
           'A reliable transport in good condition.', -- description
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
           1, -- model_id (замініть це на ID вашої моделі)
           9, -- city id
           2, -- user_id (замініть це на ID користувача, який додав цей запис)
           NOW(), -- created (поточна дата і час)
           NOW() -- last_update (поточна дата і час)
       ),
--     second transport
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable transport in good condition.', -- description
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
        2, -- model_id (замініть це на ID вашої моделі)
        4, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
--      third transport
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable transport in good condition.', -- description
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
        7, -- model_id (замініть це на ID вашої моделі)
        5, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
--      forth transport
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable transport in good condition.', -- description
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
        8, -- model_id (замініть це на ID вашої моделі)
        8, -- city id
        1, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
       --     five transport
       (true, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable transport in good condition.', -- description
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
        12, -- model_id (замініть це на ID вашої моделі)
        1, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       ),
       --     six transport
       (false, -- accident_history (boolean)
        'Sedan', -- body_type
        'Blue', -- color
        'Used', -- condition
        'A reliable transport in good condition.', -- description
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
        17, -- model_id (замініть це на ID вашої моделі)
        10, -- city id
        2, -- user_id (замініть це на ID користувача, який додав цей запис)
        NOW(), -- created (поточна дата і час)
        NOW() -- last_update (поточна дата і час)
       );

INSERT INTO transport_reviews (rating, comment, created, user_id, transport_id)
VALUES (5, 'Great vehicle!', NOW(), 1, 1),
       (4, 'Good condition.', NOW(), 2, 1),
       (5, 'Excellent choice.', NOW(), 1, 2);

INSERT INTO transport_views (user_id, transport_id, created, last_update)
VALUES (1, 1, '2023-10-08 14:00:00', NOW()),
       (2, 1, '2023-10-09 11:00:00', NOW()),
       (1, 2, '2023-10-10 09:00:00', NOW());

INSERT INTO favorite_transports (user_id, transport_id, created)
VALUES (1, 1, NOW()),
       (2, 1, NOW()),
       (2, 3, NOW());

INSERT INTO transport_galleries (image_name, is_main, url, transport_id)
VALUES ('Image 1', true, 'image1.jpg', 1),
       ('Image 2', false, 'image2.jpg', 1),
       ('Image 3', true, 'image3.jpg', 2);



INSERT INTO transport_types (id, type)
VALUES (1, 'Легкові'),
       (2, 'Мото'),
       (3, 'Вантажівки'),
       (4, 'Спецтехніка'),
       (5, 'Сільгосптехніка'),
       (6, 'Водний транспорт');


INSERT INTO transport_brands (id, brand)
VALUES
    (1, 'Toyota'), (2, 'Ford'), (3, 'Chevrolet'), (4, 'Honda'), (5, 'Volkswagen'), (6, 'Nissan'), (7, 'BMW'),
    (8, 'Mercedes-Benz'), (9, 'Audi'), (10, 'Hyundai'), (11, 'Kia'), (12, 'Subaru'), (13, 'Jeep'), (14, 'Ram'),
    (15, 'Dodge'), (16, 'GMC'), (17, 'Mazda'), (18, 'Lexus'), (19, 'Cadillac'), (20, 'Chrysler'), (21, 'Acura'),
    (22, 'Infiniti'), (23, 'Volvo'), (24, 'Land Rover'), (25, 'Mini'), (26, 'Tesla'), (27, 'Mitsubishi'),
    (28, 'Jaguar'), (29, 'Porsche'), (30, 'Buick'), (31, 'Alfa Romeo'), (32, 'Genesis'), (33, 'Maserati'),
    (34, 'Fiat'), (35, 'Ferrari'), (36, 'Lamborghini'), (37, 'Aston Martin'), (38, 'Bentley'), (39, 'Rolls-Royce'),
    (40, 'Bugatti'), (41, 'McLaren'), (42, 'Suzuki'), (43, 'Daihatsu'), (44, 'Mahindra'), (45, 'SsangYong'),
    (46, 'Dacia'), (47, 'Lancia'), (48, 'MG'), (49, 'Isuzu'), (50, 'Geely'), (51, 'Great Wall'), (52, 'FAW'),
    (53, 'Proton'), (54, 'Changan'), (55, 'Haval'), (56, 'Brilliance'), (57, 'JAC'), (58, 'ZOTYE'), (59, 'Lifan'),
    (60, 'GAZ'), (61, 'UAZ'), (62, 'Moskvitch'), (63, 'ZAZ'), (64, 'Niva'), (65, 'TagAZ'), (66, 'AvtoVAZ'),
    (67, 'LuAZ'), (68, 'BelAZ'), (69, 'МАЗ'), (70, 'Skoda'), (71, 'Peugeot'), (72, 'Citroen'), (73, 'Renault'),
    (74, 'Opel'), (77, 'Lada'), (78, 'Zastava'), (79, 'SEAT'), (80, 'Alpina'),
    (81, 'Maybach'), (82, 'Smart'), (83, 'Borgward'), (84, 'Pontiac'), (85, 'Oldsmobile'), (86, 'Mercury'),
    (87, 'Plymouth'), (88, 'Saab'), (89, 'Packard'), (90, 'Studebaker'), (91, 'Nash'), (92, 'Hudson'),
    (93, 'DeSoto'), (94, 'Edsel'), (95, 'Rambler'), (96, 'Kaiser'), (97, 'Willys'), (98, 'Crosley'),
    (99, 'Tucker'), (100, 'Duesenberg'), (101, 'Harley-Davidson'), (102, 'Kawasaki'), (103, 'Yamaha'),
    (104, 'Ducati'), (105, 'KTM'), (106, 'Triumph'), (107, 'Aprilia'), (108, 'Husqvarna'), (109, 'Moto Guzzi'),
    (110, 'Indian Motorcycle'), (111, 'Royal Enfield'), (112, 'Piaggio'), (113, 'Vespa'), (114, 'Kymco'),
    (115, 'Can-Am'), (116, 'Polaris'), (117, 'Zero Motorcycles'), (118, 'MV Agusta'), (119, 'Benelli'),
    (120, 'Bimota'), (121, 'Hyosung'), (122, 'Ural Motorcycles'), (123, 'CFMoto'), (124, 'EBR (Erik Buell Racing'),
    (125, 'GasGas'), (126, 'SWM Motorcycles'), (127, 'Sherco'), (128, 'Beta Motor'), (129, 'Oset'),
    (130, 'Energica'), (131, 'Bultaco'), (132, 'Fantic Motor'), (133, 'Rieju'), (134, 'TM Racing'),
    (135, 'Sur-Ron'), (136, 'Ossa'), (137, 'Scorpa'), (138, 'Vertigo Motors'), (139, 'Mondial'),
    (140, 'Jawa Motorcycles'), (141, 'Husaberg'), (142, 'Brough Superior'), (143, 'Arch Motorcycle'),
    (144, 'Norton Motorcycles'), (145, 'Confederate Motorcycles'), (146, 'Vyrus'), (147, 'Scania'), (148, 'MAN'),
    (149, 'DAF'), (150, 'Renault Trucks'), (151, 'Iveco'), (152, 'International'), (153, 'Kenworth'),
    (154, 'Freightliner'), (155, 'Peterbilt'), (156, 'Isuzu'), (157, 'Hino'), (158, 'Mitsubishi Fuso'),
    (159, 'Mack'), (160, 'Western Star'), (161, 'Navistar'), (162, 'Tata Motors'), (163, 'Ashok Leyland'),
    (164, 'Eicher Motors'), (165, 'Kamaz'), (166, 'ZF Friedrichshafen'), (167, 'PACCAR'), (168, 'JAC Motors'),
    (169, 'Sinotruk'), (170, 'Dongfeng Motor'), (171, 'Shaanxi'), (172, 'Foton'), (173, 'CAMC'), (174, 'Shacman'),
    (175, 'Beiben'), (176, 'JMC'), (177, 'Forland'), (178, 'FAW'), (179, 'Howo'), (180, 'King Long'),
    (181, 'Dongfeng Liuzhou'), (182, 'Dayun'), (183, 'XCMG'), (184, 'Sany'), (185, 'Sea Ray'),(186, 'Bayliner'),
    (187, 'Boston Whaler'),(188, 'Grady-White'),(189, 'Four Winns'),(190, 'Regal'),(191, 'MasterCraft'),(192, 'Malibu Boats'),
    (193, 'Nautique'),(194, 'Chaparral'),(195, 'Crownline'),(196, 'Robalo'),(197, 'Bennington'),(198, 'Harris Pontoons'),(199, 'Lund Boat');




INSERT INTO transport_type_brands (type_id, brand_id)
SELECT 1 AS type_id, id AS brand_id
FROM transport_brands
WHERE brand IN (
                'Toyota', 'Ford', 'Chevrolet', 'Honda', 'Volkswagen', 'Nissan', 'BMW', 'Mercedes-Benz', 'Audi',
                'Hyundai', 'Kia',
                'Subaru', 'Jeep', 'Ram', 'Dodge', 'GMC', 'Mazda', 'Lexus', 'Cadillac', 'Chrysler', 'Acura', 'Infiniti',
                'Volvo',
                'Land Rover', 'Mini', 'Tesla', 'Mitsubishi', 'Jaguar', 'Porsche', 'Buick', 'Alfa Romeo', 'Genesis',
                'Maserati',
                'Fiat', 'Ferrari', 'Lamborghini', 'Aston Martin', 'Bentley', 'Rolls-Royce', 'Bugatti', 'McLaren', 'Suzuki',
                'Skoda', 'Peugeot', 'Citroen', 'Renault', 'Opel', 'SEAT'
    );

INSERT INTO transport_type_brands (type_id, brand_id)
SELECT 2 AS type_id, id AS brand_id
FROM transport_brands
WHERE brand IN (
                'Harley-Davidson', 'Kawasaki', 'Yamaha', 'Ducati', 'KTM', 'Triumph', 'Aprilia', 'Husqvarna', 'Honda', 'BMW'
    );


INSERT INTO transport_type_brands (type_id, brand_id)
SELECT 3 AS type_id, id AS brand_id
FROM transport_brands
WHERE brand IN (
                'MAN', 'DAF', 'Renault Trucks', 'Iveco', 'International', 'Tesla');

INSERT INTO transport_type_brands (type_id, brand_id)
SELECT 4 AS type_id, id AS brand_id
FROM transport_brands
WHERE brand IN (
                'Zoomlion', 'Terex', 'Bell Equipment', 'LiuGong', 'Palfinger', 'Manitou',
                'Takeuchi', 'Bobcat', 'Kobelco', 'Sany'
    );

INSERT INTO transport_type_brands (type_id, brand_id)
SELECT 5 AS type_id, id AS brand_id
FROM transport_brands
WHERE brand IN ('МАЗ', 'BelAZ', 'ЗАЗ', 'Niva', 'UAZ', 'GAZ', 'AvtoVAZ');

INSERT INTO transport_type_brands (type_id, brand_id)
SELECT 6 AS type_id, id AS brand_id
FROM transport_brands
WHERE brand IN ( 'Yamaha', 'Kawasaki', 'Honda');


INSERT INTO body_types (body_type, transport_type)
VALUES ('Універсал', 1),
       ('Седан', 1),
       ('Хетчбек', 1),
       ('Позашляховик/Кросовер', 1),
       ('Купе', 1),
       ('Кабріолет', 1),
       ('Мінівен', 1),
       ('Пікап', 1),
       ('Лімузин', 1),
       ('Ліфтбек', 1),
       ('Родстер', 1),
       ('Фістбек', 1),
       ('Мікровен', 1);

INSERT INTO body_types (body_type, transport_type)
VALUES ('Мотоцикл', 2),
       ('Мотоцикл з коляскою', 2),
       ('Мотоцикл Кросс', 2),
       ('Квадроцикл', 2),
       ('Скутер', 2),
       ('Снігохід', 2),
       ('Гольф-кар', 2),
       ('Мопед', 2),
       ('Спортбайк', 2),
       ('Інша мототехніка', 2);

INSERT INTO body_types (body_type, transport_type)
VALUES ('Автовоз', 3),
       ('Вантажний фургон', 3),
       ('Вантажопасажирський фургон', 3),
       ('Для перевезення тварин', 3),
       ('Тягач', 3),
       ('Інша вантажівка', 3);

INSERT INTO body_types (body_type, transport_type)
VALUES ('Аварійно-ремонтні машини', 4),
       ('Автогрейдер', 4),
       ('Автомобіль швидкої допомоги', 4),
       ('Бульдозер', 4),
       ('Екскаватор', 4),
       ('Комунальна техніка', 4),
       ('Всюдихід', 4),
       ('Катафалк', 4),
       ('Інша спецтехніка', 4),
       ('Інша будівельна техніка', 4);

INSERT INTO body_types (body_type, transport_type)
VALUES ('Садова технікау', 5),
       ('Дрібна техніка для городу', 5),
       ('Комбайн', 5),
       ('Трактор', 5),
       ('Мінітрактор', 5),
       ('Інша сільгосптехніка', 5);

INSERT INTO body_types (body_type, transport_type)
VALUES ('Гідроцикл', 6),
       ('Яхта', 6),
       ('Катер', 6),
       ('Човен', 6),
       ('Інша водна техніка', 6);


INSERT INTO drive_types (drive_type, transport_type)
VALUES ('Повний', 1),
       ('Передній', 1),
       ('Задній', 1);

INSERT INTO drive_types (drive_type, transport_type)
VALUES ('Кардан', 2),
       ('Ремінь', 2),
       ('Ланцюг', 2);

INSERT INTO drive_types (drive_type, transport_type)
VALUES ('Повний', 3),
       ('Передній', 3),
       ('Задній', 3);

INSERT INTO drive_types (drive_type, transport_type)
VALUES ('Повний', 4),
       ('Передній', 4),
       ('Задній', 4);

INSERT INTO drive_types (drive_type, transport_type)
VALUES ('Повний', 5),
       ('Передній', 5),
       ('Задній', 5);


INSERT INTO fuel_types (fuel_type)
VALUES ('Бензин'),
       ('Газ'),
       ('Газ метан/Бензин'),
       ('Газ пропан-бутан/Бензин'),
       ('Гібрид(HEV)'),
       ('Гібрид(PHEV)'),
       ('Дизель'),
       ('Електро');

INSERT INTO transmissions (transmission)
VALUES ('Ручна/Механіка'),
       ('Автомат'),
       ('Типтронік'),
       ('Робот'),
       ('Варіатор');

INSERT INTO transport_colors (color)
VALUES ('Чорний'),
       ('Мокрий асфальт'),
       ('Білий'),
       ('Синій'),
       ('Коричневий'),
       ('Зелений'),
       ('Сірий'),
       ('Помаранчевий'),
       ('Фіолетовий'),
       ('Червоний'),
       ('Бежевий'),
       ('Жовтий');

INSERT INTO number_axles (number_axles)
VALUES ('1'),
       ('2'),
       ('3'),
       ('4'),
       ('більше 4');

INSERT INTO producing_countries (country)
VALUES
    ('Китай'),
    ('Сполучені Штати Америки'),
    ('Японія'),
    ('Індія'),
    ('Німеччина'),
    ('Корея'),
    ('Бразилія'),
    ('Мексико'),
    ('Франція'),
    ('Канада'),
    ('Італія'),
    ('Велика Британія'),
    ('Росія'),
    ('Таїланд'),
    ('Австралія'),
    ('Туреччина'),
    ('Індонезія'),
    ('Тайвань'),
    ('Польща'),
    ('Іспанія'),
    ('Нідерланди'),
    ('Швеція'),
    ('Бельгія'),
    ('Швейцарія'),
    ('Австрія'),
    ('Португалія'),
    ('Угорщина'),
    ('Чехія'),
    ('Україна'),
    ('Пакистан');

INSERT INTO transport_conditions (condition)
VALUES
    ('Повністю непошкоджене'),
    ('Професійно відремонтовані пошкодження'),
    ('Не відремонтовані пошкодження'),
    ('Не на ходу / На запчастини');

INSERT INTO wheel_configurations (wheel_configuration)
VALUES
    ('4х2'),
    ('4х4'),
    ('6х2'),
    ('6х4'),
    ('6х6'),
    ('8х2'),
    ('8х4'),
    ('8х6'),
    ('8х8'),
    ('10х4'),
    ('10х8');
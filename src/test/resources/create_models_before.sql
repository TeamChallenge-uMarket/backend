DELETE
FROM "uAutoTest".public.type_cars;

DELETE
FROM "uAutoTest".public.brand_cars;

DELETE
FROM "uAutoTest".public.models;


INSERT INTO "uAutoTest".public.type_cars (id, type)
VALUES (1, 'легкові'),
       (2, 'мототранспорт'),
       (3, 'електротранспорт'),
       (4, 'причепи'),
       (5, 'вантажівки'),
       (6, 'водний транспорт');

INSERT INTO "uAutoTest".public.brand_cars (brand, type_id)
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
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Corolla', 1),
       ('Camry', 1),
       ('RAV4', 1),
       ('Prius', 1),
       ('Highlander', 1),
       ('Sienna', 1);

-- Models for Honda
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Civic', 2),
       ('Accord', 2),
       ('CR-V', 2),
       ('Fit', 2),
       ('HR-V', 2);

-- Models for Ford
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('F-150', 3),
       ('Focus', 3),
       ('Escape', 3),
       ('Explorer', 3),
       ('Mustang', 3);

-- Models for Chevrolet
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Silverado', 4),
       ('Malibu', 4),
       ('Equinox', 4),
       ('Cruze', 4),
       ('Traverse', 4);

-- Models for Nissan
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Altima', 5),
       ('Maxima', 5),
       ('Rogue', 5),
       ('Sentra', 5),
       ('Pathfinder', 5);

-- Models for BMW
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('3 Series', 6),
       ('5 Series', 6),
       ('X3', 6),
       ('X5', 6),
       ('7 Series', 6);

-- Models for Mercedes-Benz
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('C-Class', 7),
       ('E-Class', 7),
       ('GLC', 7),
       ('GLE', 7),
       ('S-Class', 7);

-- Models for Audi
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('A3', 8),
       ('A4', 8),
       ('Q5', 8),
       ('Q7', 8),
       ('A8', 8);

-- Models for Volkswagen
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Jetta', 9),
       ('Passat', 9),
       ('Tiguan', 9),
       ('Atlas', 9),
       ('Golf', 9);

-- Models for Hyundai
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Elantra', 10),
       ('Sonata', 10),
       ('Tucson', 10),
       ('Santa Fe', 10),
       ('Kona', 10);

-- Models for Kia
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Forte', 11),
       ('Optima', 11),
       ('Sportage', 11),
       ('Sorento', 11),
       ('Soul', 11);

-- Models for Subaru
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Impreza', 12),
       ('Legacy', 12),
       ('Outback', 12),
       ('Forester', 12),
       ('Crosstrek', 12);

-- Models for Mazda
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('Mazda3', 13),
       ('Mazda6', 13),
       ('CX-5', 13),
       ('MX-5 Miata', 13),
       ('CX-9', 13);

-- Models for Lexus
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('IS', 14),
       ('ES', 14),
       ('RX', 14),
       ('NX', 14),
       ('GX', 14);

-- Models for Acura
INSERT INTO "uAutoTest".public.models (model, brand_id)
VALUES ('ILX', 15),
       ('TLX', 15),
       ('MDX', 15),
       ('RDX', 15),
       ('NSX', 15);
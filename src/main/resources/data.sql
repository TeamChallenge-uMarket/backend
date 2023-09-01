INSERT INTO parent_categories (description, name)
VALUES ('дитячий одяг', 'одяг'),
       ('0-1 роки дітям', 'для малюка'),
       ('гігієна для дітей', 'гігієна'),
       ('все для вашої дитини', 'розвиток та креативність'),
       ('дитячі іграшки', 'іграшки'),
       ('все необхідне для школи', 'все для школи'),
       ('вся їжа для дітей', 'їжа та годування');

INSERT INTO categories (parent_id, description, name)
VALUES (1, '1-3 роки дитинство', 'одяг для малюка'),
       (1, '3-5 років дитинство', 'одяг для молодших дітей'),
       (1, '5 років і старше дитинство', 'одяг для підлітків'),
       (2, '0+ немовля', 'памперси'),
       (2, '0+ дитячий одяг', 'одяг для малюка'),
       (2, '0+ дитяча їжа', 'їжа для малюка'),
       (3, 'мило і крем', 'догляд за здоров''ям'),
       (3, 'вологі серветки', 'догляд за шкірою'),
       (4, 'іграшки для малюків', 'іграшки для немовлят'),
       (4, '3+ книжки з зображеннями', 'книги'),
       (5, 'пазли та конструктори', 'логічні іграшки'),
       (5, 'барбі та машинки', 'іграшки для дівчаток і хлопчиків'),
       (6, 'зошити та щоденники', 'зошити'),
       (6, 'ручки та олівці', 'ручки'),
       (7, '1-3 роки дитяча їжа', 'їжа'),
       (7, 'сухе молоко', 'сухе молоко');
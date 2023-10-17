INSERT INTO users (name, email, password, phone, refresh_token, city, created, last_update, active)
VALUES ('Test-User',
        'test.email@example.com', -- Замініть на реальний email користувача
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
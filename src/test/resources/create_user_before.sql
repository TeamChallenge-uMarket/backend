DELETE
FROM "uAutoTest".public.role_has_permissions;

DELETE
FROM "uAutoTest".public.user_has_permissions;

DELETE
FROM "uAutoTest".public.roles;

DELETE
FROM "uAutoTest".public.permissions;

DELETE
FROM "uAutoTest".public.users;


INSERT INTO "uAutoTest".public.users(id, created, last_update,
                  email, name, password, phone,
                  refresh_token, city)
VALUES (2, '2023-09-27 01:02:29.892836', '2023-10-03 17:37:59.276989',
        'praice07@gmail.com', 'Oleh11',
        'password11', '+380982704401',
        'refresh_token_value',
        12),

        --second user
       (1, '2023-09-27 01:02:29.892836', '2023-10-03 17:37:59.276989',
        'test@gmail.com', 'testUser',
        'password1', '+380989922333',
        'refresh_token_value',
        10);

INSERT INTO "uAutoTest".public.roles (name, created)
VALUES ('USER', '2023-09-27 01:02:29.892836'),
       ('ADMIN', '2023-09-27 01:02:29.892836'),
       ('MODERATOR', '2023-09-27 01:02:29.892836'),
       ('GUEST', '2023-09-27 01:02:29.892836');

INSERT INTO "uAutoTest".public.permissions (description, created)
VALUES ('Permission 1 Description', '2023-09-27 01:02:29.892836'),
       ('Permission 2 Description', '2023-09-27 01:02:29.892836'),
       ('Permission 3 Description', '2023-09-27 01:02:29.892836');

INSERT INTO "uAutoTest".public.user_has_roles (user_id, role_id)
VALUES (1, 1), -- User1
       (2, 1), -- User2
       (2, 2);

INSERT INTO "uAutoTest".public.user_has_permissions (user_id, permissions_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3);

INSERT INTO "uAutoTest".public.role_has_permissions (role_id, permissions_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (4, 1);

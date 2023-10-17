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
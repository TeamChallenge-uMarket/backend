CREATE TABLE IF NOT EXISTS regions
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cities
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    region_id   BIGINT,
    FOREIGN KEY (region_id) REFERENCES regions (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    phone VARCHAR(20),
    refresh_token VARCHAR(255),
    city BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6),
    FOREIGN KEY (city) REFERENCES cities (id)
);

CREATE TABLE IF NOT EXISTS permissions
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (name IN ('USER', 'ADMIN', 'MODERATOR', 'GUEST')),
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6)
);

CREATE TABLE IF NOT EXISTS role_has_permissions
(
    id             BIGSERIAL PRIMARY KEY,
    permissions_id BIGINT,
    role_id        BIGINT,
    FOREIGN KEY (permissions_id) REFERENCES permissions (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

create table if not exists users
(
    id            bigserial,
    created       timestamp(6) not null,
    last_update   timestamp(6),
    email         varchar(255),
    name          varchar(255),
    password      varchar(255),
    phone         varchar(255),
    refresh_token varchar(255),
    city          bigint,
    constraint users_pkey
        primary key (id),
    constraint users_email_uindex
        unique (email),
    constraint users_cities_id_fk
        foreign key (city) references cities
);



CREATE TABLE IF NOT EXISTS user_has_permissions
(
    id             BIGSERIAL PRIMARY KEY,
    permissions_id BIGINT,
    user_id        BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6),
    FOREIGN KEY (permissions_id) REFERENCES permissions (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS user_has_roles
(
    id      BIGSERIAL PRIMARY KEY,
    role_id BIGINT,
    user_id BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS type_cars
(
    id   BIGSERIAL PRIMARY KEY,
    type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS brand_cars
(
    id      BIGSERIAL PRIMARY KEY,
    brand   VARCHAR(255),
    type_id BIGINT,
    FOREIGN KEY (type_id) REFERENCES type_cars (id)
);

CREATE TABLE IF NOT EXISTS models
(
    id       BIGSERIAL PRIMARY KEY,
    model    VARCHAR(255),
    brand_id BIGINT,
    FOREIGN KEY (brand_id) REFERENCES brand_cars (id)
);

CREATE TABLE IF NOT EXISTS cars
(
    id                  BIGSERIAL PRIMARY KEY,
    year                INTEGER,
    mileage             INTEGER,
    body_type           VARCHAR(255),
    vincode             VARCHAR(255),
    description         TEXT,
    transmission        VARCHAR(255),
    fuel_type           VARCHAR(255),
    engine_displacement DOUBLE PRECISION,
    engine_power        INTEGER,
    drive_type          VARCHAR(255),
    number_of_doors     INTEGER,
    number_of_seats     INTEGER,
    color               VARCHAR(255),
    imported_from       VARCHAR(255),
    accident_history    BOOLEAN,
    condition           VARCHAR(255),
    fuel_consumption_city    DOUBLE PRECISION,
    fuel_consumption_highway DOUBLE PRECISION,
    fuel_consumption_mixed   DOUBLE PRECISION,
    price               NUMERIC(38, 2),
    bargain             BOOLEAN,
    trade               BOOLEAN,
    military            BOOLEAN,
    installment_payment BOOLEAN,
    uncleared           BOOLEAN,
    model_id            BIGINT,
    user_id             BIGINT,
    city             BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6)
);

ALTER TABLE cars
    ADD CONSTRAINT fk_model
        FOREIGN KEY (model_id) REFERENCES models (id);

ALTER TABLE cars
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE cars
    ADD CONSTRAINT fk_city
        FOREIGN KEY (city) REFERENCES cities (id);

CREATE TABLE IF NOT EXISTS car_favorites
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    car_id  BIGINT,
    created TIMESTAMP(6) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE IF NOT EXISTS car_galleries
(
    id         BIGSERIAL PRIMARY KEY,
    image_name VARCHAR(255),
    is_main    BOOLEAN,
    url        VARCHAR(500),
    car_id     BIGINT,
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE IF NOT EXISTS car_reviews
(
    id      BIGSERIAL PRIMARY KEY,
    rating  INTEGER      NOT NULL,
    comment VARCHAR(255),
    created TIMESTAMP(6) NOT NULL,
    user_id BIGINT,
    car_id  BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE IF NOT EXISTS car_views
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    car_id  BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

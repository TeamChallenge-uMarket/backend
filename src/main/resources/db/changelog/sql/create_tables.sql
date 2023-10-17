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


CREATE TABLE IF NOT EXISTS body_types
(
    id        BIGSERIAL PRIMARY KEY,
    body_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transmissions
(
    id           BIGSERIAL PRIMARY KEY,
    transmission VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS fuel_types
(
    id        BIGSERIAL PRIMARY KEY,
    fuel_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS drive_types
(
    id         BIGSERIAL PRIMARY KEY,
    drive_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transport_colors
(
    id    BIGSERIAL PRIMARY KEY,
    color VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS producing_countries
(
    id      BIGSERIAL PRIMARY KEY,
    country VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS transport_conditions
(
    id        BIGSERIAL PRIMARY KEY,
    condition VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wheel_configurations
(
    id                  BIGSERIAL PRIMARY KEY,
    wheel_configuration VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS number_axles
(
    id           BIGSERIAL PRIMARY KEY,
    number_axles VARCHAR(255) NOT NULL
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
    active        boolean not null default false,
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

CREATE TABLE IF NOT EXISTS transport_types
(
    id   BIGSERIAL PRIMARY KEY,
    type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transport_brands
(
    id      BIGSERIAL PRIMARY KEY,
    brand   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS transport_type_brands
(
    id             BIGSERIAL PRIMARY KEY,
    brand_id       BIGINT,
    type_id        BIGINT,
    FOREIGN KEY (brand_id) REFERENCES transport_brands (id),
    FOREIGN KEY (type_id) REFERENCES transport_types (id)
);



CREATE TABLE IF NOT EXISTS transport_models
(
    id       BIGSERIAL PRIMARY KEY,
    model    VARCHAR(255),
    type_brand_id BIGINT,
    FOREIGN KEY (type_brand_id) REFERENCES transport_type_brands (id)
);

CREATE TABLE IF NOT EXISTS transports
(
    id                  BIGSERIAL PRIMARY KEY,
    year                INTEGER,
    mileage             INTEGER,
    vincode             VARCHAR(255),
    description         TEXT,
    engine_displacement DOUBLE PRECISION,
    engine_power        INTEGER,
    number_of_doors     INTEGER,
    number_of_seats     INTEGER,
    accident_history    BOOLEAN,
    fuel_consumption_city    DOUBLE PRECISION,
    fuel_consumption_highway DOUBLE PRECISION,
    fuel_consumption_mixed   DOUBLE PRECISION,
    load_capacity       INTEGER,
    price               NUMERIC(38, 2),
    bargain             BOOLEAN,
    trade               BOOLEAN,
    military            BOOLEAN,
    installment_payment BOOLEAN,
    uncleared           BOOLEAN,
    model_id            BIGINT,
    user_id             BIGINT,
    city                BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6),
    body_type           BIGINT,
    drive_type          BIGINT,
    fuel_type           BIGINT,
    producing_country   BIGINT,
    transmission        BIGINT,
    transport_color     BIGINT,
    transport_condition BIGINT,
    number_axles        BIGINT,
    wheel_configuration BIGINT
);

ALTER TABLE transports
    ADD CONSTRAINT fk_model
        FOREIGN KEY (model_id) REFERENCES transport_models (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_city
        FOREIGN KEY (city) REFERENCES cities (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_body_type
        FOREIGN KEY (body_type) REFERENCES body_types (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_drive_type
        FOREIGN KEY (drive_type) REFERENCES drive_types (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_fuel_type
        FOREIGN KEY (fuel_type) REFERENCES fuel_types (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_producing_country
        FOREIGN KEY (producing_country) REFERENCES producing_countries (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_transmission
        FOREIGN KEY (transmission) REFERENCES transmissions (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_transport_color
        FOREIGN KEY (transport_color) REFERENCES transport_colors (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_transport_condition
        FOREIGN KEY (transport_condition) REFERENCES transport_conditions (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_number_axles
        FOREIGN KEY (number_axles) REFERENCES number_axles (id);

ALTER TABLE transports
    ADD CONSTRAINT fk_wheel_configuration
        FOREIGN KEY (wheel_configuration) REFERENCES wheel_configurations (id);


CREATE TABLE IF NOT EXISTS favorite_transports
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    transport_id  BIGINT,
    created TIMESTAMP(6) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (transport_id) REFERENCES transports (id)
);

CREATE TABLE IF NOT EXISTS transport_galleries
(
    id         BIGSERIAL PRIMARY KEY,
    image_name VARCHAR(255),
    is_main    BOOLEAN,
    url        VARCHAR(500),
    transport_id     BIGINT,
    FOREIGN KEY (transport_id) REFERENCES transports (id)
);

CREATE TABLE IF NOT EXISTS transport_reviews
(
    id      BIGSERIAL PRIMARY KEY,
    rating  INTEGER      NOT NULL,
    comment VARCHAR(255),
    created TIMESTAMP(6) NOT NULL,
    user_id BIGINT,
    transport_id  BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (transport_id) REFERENCES transports (id)
);

CREATE TABLE IF NOT EXISTS transport_views
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    transport_id  BIGINT,
    created             TIMESTAMP(6),
    last_update         TIMESTAMP(6),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (transport_id) REFERENCES transports (id)
);

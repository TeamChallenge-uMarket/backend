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

CREATE TABLE IF NOT EXISTS permissions
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) CHECK (name IN ('USER', 'ADMIN', 'MODERATOR', 'GUEST'))
);

CREATE TABLE IF NOT EXISTS role_has_permissions
(
    id             BIGSERIAL PRIMARY KEY,
    permissions_id BIGINT,
    role_id        BIGINT,
    FOREIGN KEY (permissions_id) REFERENCES permissions (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS user_has_permissions
(
    id             BIGSERIAL PRIMARY KEY,
    permissions_id BIGINT,
    user_id        BIGINT,
    FOREIGN KEY (permissions_id) REFERENCES permissions (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS user_has_roles
(
    id      BIGSERIAL PRIMARY KEY,
    role_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS fuel_consumption
(
    id      BIGSERIAL PRIMARY KEY,
    city    DOUBLE PRECISION,
    highway DOUBLE PRECISION,
    mixed   DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS cars
(
    id                  BIGSERIAL PRIMARY KEY,
    year                INTEGER,
    mileage             INTEGER,
    body_type           VARCHAR(255),
    address             VARCHAR(255),
    vincode             VARCHAR(255),
    description         TEXT,
    transmission        VARCHAR(255),
    fuel_type           VARCHAR(255),
    consumption_id      INTEGER,
    engine_displacement DOUBLE PRECISION,
    engine_power        INTEGER,
    drive_type          VARCHAR(255),
    number_of_doors     INTEGER,
    number_of_seats     INTEGER,
    color               VARCHAR(255),
    imported_from       VARCHAR(255),
    accident_history    BOOLEAN,
    condition           VARCHAR(255),
    gallery             TEXT,
    fuel_consumption_id  BIGINT,
    price_id            BIGINT,
    model_id            BIGINT,
    user_id             BIGINT,
    city                BIGINT,
    FOREIGN KEY (fuel_consumption_id) REFERENCES fuel_consumption (id),
    FOREIGN KEY (price_id) REFERENCES car_prices (id),
    FOREIGN KEY (model_id) REFERENCES car_models (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (city) REFERENCES cities (id)
);

CREATE TABLE IF NOT EXISTS car_galleries
(
    id         BIGSERIAL PRIMARY KEY,
    image_name VARCHAR(255),
    is_main    BOOLEAN,
    url        VARCHAR(255),
    url_small  VARCHAR(255),
    car_id     BIGINT,
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE IF NOT EXISTS car_models
(
    id       BIGSERIAL PRIMARY KEY,
    model    VARCHAR(255),
    brand_id BIGINT,
    FOREIGN KEY (brand_id) REFERENCES brand_cars (id)
);

CREATE TABLE IF NOT EXISTS brand_cars
(
    id      BIGSERIAL PRIMARY KEY,
    brand   VARCHAR(255),
    type_id BIGINT,
    FOREIGN KEY (type_id) REFERENCES car_types (id)
);

CREATE TABLE IF NOT EXISTS car_favorites
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    car_id  BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE IF NOT EXISTS car_prices
(
    id                  BIGSERIAL PRIMARY KEY,
    price               NUMERIC(38, 2),
    bargain             BOOLEAN,
    trade               BOOLEAN,
    military            BOOLEAN,
    installment_payment BOOLEAN,
    uncleared           BOOLEAN,
    car_id              BIGINT,
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

CREATE TABLE IF NOT EXISTS car_types
(
    id   BIGSERIAL PRIMARY KEY,
    type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS car_views
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    car_id  BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

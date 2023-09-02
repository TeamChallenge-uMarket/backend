create table if not exists parent_categories
(
    id          bigserial,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);

create table if not exists categories
(
    id          bigserial,
    parent_id   bigint,
    description varchar(255),
    name        varchar(255),
    primary key (id),
    constraint categories_parent_categories_id_fk
        foreign key (parent_id) references parent_categories
);

create table if not exists permissions
(
    created     timestamp(6) not null,
    id          bigserial,
    last_update timestamp(6),
    name        varchar(255),
    primary key (id)
);

create table if not exists roles
(
    created     timestamp(6) not null,
    id          bigserial,
    last_update timestamp(6),
    name        varchar(255),
    primary key (id),
    constraint roles_name_check
        check ((name)::text = ANY
               (ARRAY [('USER'::character varying)::text, ('ADMIN'::character varying)::text, ('MODERATOR'::character varying)::text, ('GUEST'::character varying)::text]))
);

create table if not exists role_has_permissions
(
    id             bigserial,
    permissions_id bigint,
    role_id        bigint,
    primary key (id),
    constraint role_has_permissions_permissions_id_fk
        foreign key (permissions_id) references permissions,
    constraint role_has_permissions_roles_id_fk
        foreign key (role_id) references roles
);

create table if not exists users
(
    created       timestamp(6) not null,
    id            bigserial,
    last_update   timestamp(6),
    city          varchar(255),
    country       varchar(255),
    email         varchar(255),
    name          varchar(255),
    password      varchar(255),
    phone         varchar(255),
    refresh_token varchar(255),
    primary key (id),
    unique (email)
);

create table if not exists products
(
    price       numeric(38, 2),
    created     timestamp(6) not null,
    id          bigserial,
    last_update timestamp(6),
    user_id     bigint,
    description varchar(255),
    name        varchar(255),
    status      varchar(255),
    primary key (id),
    constraint products_users_id_fk
        foreign key (user_id) references users,
    constraint products_status_check
        check ((status)::text = ANY
               (ARRAY [('PENDING'::character varying)::text, ('ACTIVE'::character varying)::text, ('INACTIVE'::character varying)::text]))
);

create table if not exists favorite_products
(
    id         bigserial,
    product_id bigint,
    user_id    bigint,
    primary key (id),
    constraint favorite_products_products_id_fk
        foreign key (product_id) references products,
    constraint favorite_products_users_id_fk
        foreign key (user_id) references users
);

create table if not exists orders
(
    price       numeric(38, 2),
    quantity    integer,
    created     timestamp(6) not null,
    id          bigserial,
    last_update timestamp(6),
    product_id  bigint,
    user_id     bigint,
    address     varchar(255),
    comment     varchar(255),
    phone       varchar(255),
    status      varchar(255),
    primary key (id),
    constraint orders_products_id_fk
        foreign key (product_id) references products,
    constraint orders_users_id_fk
        foreign key (user_id) references users
);

create table if not exists product_categories
(
    category_id bigint,
    id          bigserial,
    product_id  bigint,
    primary key (id),
    constraint product_categories_categories_id_fk
        foreign key (category_id) references categories,
    constraint product_categories_products_id_fk
        foreign key (product_id) references products
);

create table if not exists product_gallery
(
    is_main    boolean,
    id         bigserial,
    product_id bigint,
    url        varchar(255),
    primary key (id),
    constraint product_gallery_products_id_fk
        foreign key (product_id) references products
);

create table if not exists product_reviews
(
    rating     integer      not null,
    created    timestamp(6) not null,
    id         bigserial,
    product_id bigint,
    user_id    bigint,
    comment    varchar(255),
    primary key (id),
    constraint product_reviews_products_id_fk
        foreign key (product_id) references products,
    constraint product_reviews_users_id_fk
        foreign key (user_id) references users
);

create table if not exists user_has_permissions
(
    created        timestamp(6) not null,
    id             bigserial,
    last_update    timestamp(6),
    permissions_id bigint,
    user_id        bigint,
    primary key (id),
    constraint user_has_permissions_permissions_id_fk
        foreign key (permissions_id) references permissions,
    constraint user_has_permissions_users_id_fk
        foreign key (user_id) references users
);

create table if not exists user_has_roles
(
    created     timestamp(6) not null,
    id          bigserial,
    last_update timestamp(6),
    role_id     bigint,
    user_id     bigint,
    primary key (id),
    constraint user_has_roles_roles_id_fk
        foreign key (role_id) references roles,
    constraint user_has_roles_users_id_fk
        foreign key (user_id) references users
);
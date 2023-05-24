-- drop table if exists anastasiia_database.public.Flea;
-- drop table if exists anastasiia_database.public.Cat;
-- drop table if exists anastasiia_database.public.Owner;
--
-- create table if not exists anastasiia_database.public.Owner
-- (
--     id         bigserial primary key,
--     name       varchar(50) not null,
--     birth_date date        not null
-- );
--
-- create table if not exists anastasiia_database.public.Cat
-- (
--     id          bigserial primary key,
--     name        varchar(50) not null,
--     birth_date  date        not null,
--     breed       varchar(50) not null,
--     color       varchar(50) not null,
--     owner_id    bigint      not null references Owner (id),
--     tail_length int         not null
-- );
--
-- create table if not exists anastasiia_database.public.Flea
-- (
--     id     bigserial primary key,
--     name   varchar(50) not null,
--     cat_id bigint      not null references Cat (id)
-- );


create table if not exists public.Owner
(
    id         bigserial             not null primary key,
    name       character varying(60) not null,
    birth_date date                  not null
);

create table if not exists public.Cat
(
    id         bigserial             not null primary key,
    name       character varying(60) not null,
    birth_date date                  not null,
    breed      character varying(60) not null,
    color      character varying(60) not null,
    owner_id      bigint                not null,
    foreign key (owner_id) references Owner (id) ON DELETE CASCADE,
    tail_length int                  not null
);

create table if not exists public.Flea
(
    id         bigserial             not null primary key,
    name       character varying(60) not null,
    cat_id      bigint                  not null,
    foreign key (cat_id) references Cat (id) ON DELETE CASCADE
);

-- GRANT ALL ON DATABASE anastasiia_database TO anastasiia_user;
-- ALTER DATABASE anastasiia_database OWNER TO anastasiia_user;

-- grant all privileges on all tables in schema public to postgres;

alter table if exists public.Owner
    owner to postgres;
alter table if exists public.Cat
    owner to postgres;
alter table if exists public.Flea
    owner to postgres;
create table if not exists public.Owner
(
    id         bigserial             not null primary key,
    name       character varying(60) not null,
    birth_date date                  not null
);
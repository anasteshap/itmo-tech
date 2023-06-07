create table if not exists public.Cat
(
    id          bigserial             not null primary key,
    name        character varying(60) not null,
    birth_date  date                  not null,
    breed       character varying(60) not null,
    color       character varying(60) not null,
    owner_id    bigint                not null,
    foreign key (owner_id) references Owner (id) ON DELETE CASCADE
--     tail_length int                   not null
);
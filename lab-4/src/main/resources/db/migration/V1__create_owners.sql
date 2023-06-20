create table if not exists public.Owner
(
    id         bigserial             not null primary key,
    name       character varying(60) not null,
    birth_date date                  not null
);

create table if not exists public.User
(
    id       bigserial              not null primary key,
    username character varying(60) not null,
    password character varying(60) not null,
    role     character varying(60) not null,
    owner_id bigint,
    foreign key (owner_id) references Owner (id) ON DELETE CASCADE
);
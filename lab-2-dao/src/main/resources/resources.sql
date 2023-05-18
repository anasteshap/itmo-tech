create table if not exists anastasiia_database.public.Owner
(
    id         serial primary key,
    name       varchar(50) not null,
    birth_date date        not null
);

create table if not exists anastasiia_database.public.Cat
(
    id         serial primary key,
    name       varchar(50) not null,
    birth_date date        not null,
    breed      varchar(50) not null,
    color      varchar(50) not null,
    owner_id   bigint      not null references Owner (id)
);

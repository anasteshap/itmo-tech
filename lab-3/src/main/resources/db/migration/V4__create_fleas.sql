create table if not exists public.Flea
(
    id     bigserial             not null primary key,
    name   character varying(60) not null,
    cat_id bigint                not null,
    foreign key (cat_id) references Cat (id) ON DELETE CASCADE
);
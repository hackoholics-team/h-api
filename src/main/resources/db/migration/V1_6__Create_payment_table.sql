create table if not exists "payment" (
    id varchar primary key,
    amount integer,
    creation_datetime timestamp with time zone default now(),
    currency varchar,
    payment_method_id varchar references "payment_method"(id)
);
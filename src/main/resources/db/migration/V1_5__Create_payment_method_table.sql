create table if not exists "payment_method" (
    id varchar primary key,
    user_id varchar references "user"(id),
    cvc varchar,
    brand varchar,
    number varchar,
    exp_month integer,
    exp_year integer
);
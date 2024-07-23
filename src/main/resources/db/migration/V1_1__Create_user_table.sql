create table if not exists "user" (
    id varchar primary key,
    first_name varchar,
    last_name varchar,
    username varchar,
    birth_date timestamp with time zone,
    creation_datetime timestamp with time zone,
    email varchar not null,
    firebase_id varchar not null,
    photo_id varchar
);
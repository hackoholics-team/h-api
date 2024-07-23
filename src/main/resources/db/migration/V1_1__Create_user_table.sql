create table if not exists "user" (
    id varchar primary key,
    name varchar not null,
    email varchar not null,
    firebase_id varchar not null,
    photo_id varchar
);
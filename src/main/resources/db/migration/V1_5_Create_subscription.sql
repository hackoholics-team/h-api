create table if not exists "subscription"(
    id varchar primary key ,
    creation_datetime timestamp with time zone,
    user_id varchar references "user"(id),
    subscribe_type subscribe_type default 'FREE'
);

CREATE TYPE subscribe_type AS ENUM ('FREE', 'PREMIUM');
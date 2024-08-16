create table if not exists "subscription"(
    id varchar primary key ,
    creation_datetime timestamp with time zone,
    user_id varchar references "user"(id),
    subscribe_type VARCHAR(10) CHECK (subscription.subscribe_type IN ('FREE', 'PREMIUM'))
);
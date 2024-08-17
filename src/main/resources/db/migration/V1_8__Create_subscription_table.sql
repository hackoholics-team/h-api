DO
$$
    begin
        if not exists (select from pg_type where typname = 'subscription_type') then
            create type subscription_type as ENUM ('FREE', 'PREMIUM');
        end if;
    end
$$;

create table if not exists "subscription"(
    id varchar primary key ,
    creation_datetime timestamp with time zone,
    user_id varchar references "user"(id),
    subscription_type subscription_type default 'FREE'
);
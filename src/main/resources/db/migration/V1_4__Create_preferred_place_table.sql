create table if not exists "preferred_place" (
    id varchar primary key,
    user_id varchar references "user"(id)
);
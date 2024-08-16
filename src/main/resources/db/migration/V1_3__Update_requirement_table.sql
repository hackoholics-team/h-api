alter table "requirement" add constraint requirement_user_fk
foreign key (id) references "user"(id);
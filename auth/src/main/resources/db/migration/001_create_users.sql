CREATE TABLE "users" (
id serial not null primary key,
uuid text not null,
login text not null,
password text not null,
email text not null,
role text not null,
is_locked boolean DEFAULT true,
is_enabled boolean DEFAULT false
)
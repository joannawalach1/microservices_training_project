CREATE TABLE "Users" (
id serial not null primary key,
uuid text not null,
login text not null,
password text not null,
email text not null,
role text not null,
isLocked boolean DEFAULT true,
isEnabled boolean DEFAULT false
)
CREATE TABLE "Users"(
id serial NOT NULL PRIMARY KEY,
uuid text NOT NULL,
login text NOT NULL,
login text NOT NULL,
email text NOT NULL,
password text NOT NULL,
role text NOT NUll,
isLocked boolean true,
isEnable boolean false
)
CREATE TABLE "category_parameters"(
id serial primary key,
short_id text not null,
category_name text not null
)

CREATE TABLE "products"(
id serial primary key,
uuid text not null,
activated boolean DEFAULT false,
product_name text not null,
main_desc text not null,
desc_html text not null,
price decimal(10,2) not null,
image_urls text[] not null,
parameters text,
created_at date,
updated_at date,
category_parameters integer REFERENCES "category_parameters(id)"
)
"
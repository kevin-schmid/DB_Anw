set search_path to ue2;

drop table beer;
drop table recipe_material;
drop table recipe;
drop table material;

create table material (
	material_id serial primary key,
    name varchar(255) not null
);

create table recipe (
	recipe_id serial primary key,
    name varchar(255) not null,
    instruction varchar(1023) not null
);

create table recipe_material (
	recipe_id int references recipe(recipe_id),
    material_id int references material(material_id),
    quantity int not null,
    primary key(recipe_id, material_id)
);

create table beer (
	beer_id serial primary key,
    name varchar(255) not null,
    seasonal boolean not null,
    beer_type varchar(255),
    vol decimal not null,
    original_wort varchar(255) not null,
	recipe_id int references recipe(recipe_id)
);
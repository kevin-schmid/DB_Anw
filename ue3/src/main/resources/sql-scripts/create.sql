set search_path to ue4;

drop table beer;
drop table recipe;
drop sequence seq_beer;

create sequence seq_beer start with 1000 increment by 1;

create table recipe (
  recipe_id serial primary key,
  name varchar(255) not null
);

create table beer (
  beer_id int primary key default nextval('seq_beer'),
  name varchar(255) not null,
  beer_type varchar(255),
  recipe_id int references recipe(recipe_id) unique
);

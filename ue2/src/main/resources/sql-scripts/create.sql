set search_path to ue2;

drop sequence seq_beer;
drop table beer;
drop table ingredient;

create sequence seq_beer start with 1000 increment by 1;

create table ingredient (
  ingredient_id serial primary key,
  name varchar(255) not null
);

create table beer (
  beer_id int primary key default nextval('seq_beer'),
  name varchar(255) not null,
  beer_type varchar(255)
);

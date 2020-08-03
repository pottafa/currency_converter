
drop table if exists currencies cascade;
drop table if exists exchange_rates cascade;
drop table if exists operations cascade;
drop table if exists users cascade;
drop table if exists roles cascade;
drop table if exists users_roles cascade;

create table IF NOT EXISTS currencies (id char(3) not null, valute_id varchar(255), name varchar(255), num_code char(3), primary key (id));
create table IF NOT EXISTS exchange_rates (id int8 not null, date date, nominal int8, value decimal(19, 4), currency_id varchar(255), primary key (id));
create table IF NOT EXISTS operations (id int8 not null, date date, original_amount decimal(19, 4), original_currency char(3), received_amount decimal(19, 4), target_currency char(3), primary key (id));
create table IF NOT EXISTS roles (id int8 not null, name varchar(255), primary key (id));
create table IF NOT EXISTS users (id int8 not null, login varchar(255), password varchar(255), primary key (id));
create table IF NOT EXISTS users_roles (user_id int8 not null, roles_id int8 not null, primary key (user_id, roles_id));

insert into roles (id, name) values ('0', 'ROLE_USER' );
insert into roles (id, name) values ('1', 'ROLE_ADMIN' );

/*
Admin creation
Password: root
*/
insert into users (id, login, password) values (1, 'admin', '$2a$04$0GVs3/mFs1j9JocpyZWF3OBS.gs5eyww.GYX3W5TZ1TQj7cP6P6Fm' );
insert into users (id, login, password) values (2 , 'user', '$2a$04$0GVs3/mFs1j9JocpyZWF3OBS.gs5eyww.GYX3W5TZ1TQj7cP6P6Fm'  );

insert into users_roles (user_id, roles_id) values (1, 1 );
insert into users_roles (user_id, roles_id) values (2, 0 );




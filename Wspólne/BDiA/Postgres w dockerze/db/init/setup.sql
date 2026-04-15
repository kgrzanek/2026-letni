drop schema if exists public cascade;
create schema bdia;

-- create owner user with all privileges
drop user if exists bdia_owner;
create user bdia_owner with password '12345' login;

-- grant all necessary privileges in one step
alter database bdia owner to bdia_owner;
grant all privileges on schema bdia to bdia_owner;

grant all privileges on all tables in schema bdia to bdia_owner;

grant all privileges on all sequences in schema bdia to bdia_owner;                                                         alter default privileges in schema bdia grant all on sequences to bdia_owner;

grant all privileges on all functions in schema bdia to bdia_owner;                                                         alter default privileges in schema bdia grant all on functions to bdia_owner;

grant usage on schema bdia to bdia_owner;
alter default privileges in schema bdia grant all on tables to bdia_owner;

-- set search path
alter user bdia_owner set search_path to bdia;

-- table used by Proste JDBC examples
create table bdia.people (
    id   bigserial    not null primary key,
    name varchar(100) not null,
    age  integer      not null
);

insert into bdia.people (name, age) values
    ('Anna',  25),
    ('Bob',   30),
    ('Carol', 22);

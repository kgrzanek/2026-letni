-- User profiles table for demonstrating transactions and isolation levels.
-- Run as bdia_owner against the bdia database.

set search_path to bdia;

drop table if exists user_profiles;

create table user_profiles (
    id       bigserial   not null primary key,
    username varchar(50) not null unique,
    email    varchar(100) not null unique,
    balance  numeric(12, 2) not null default 0.00,
    status   varchar(20) not null default 'active'
                         check (status in ('active', 'suspended', 'closed'))
);

insert into user_profiles (username, email, balance, status) values
    ('alice',   'alice@example.com',   1500.00, 'active'),
    ('bob',     'bob@example.com',      320.50, 'active'),
    ('carol',   'carol@example.com',   9800.00, 'active'),
    ('dave',    'dave@example.com',       0.00, 'suspended'),
    ('eve',     'eve@example.com',     4200.75, 'active'),
    ('frank',   'frank@example.com',    750.00, 'active'),
    ('grace',   'grace@example.com',  12000.00, 'active'),
    ('heidi',   'heidi@example.com',     50.25, 'active'),
    ('ivan',    'ivan@example.com',    3300.00, 'suspended'),
    ('judy',    'judy@example.com',    6100.00, 'closed');

-- Quick sanity check
select * from user_profiles order by id;

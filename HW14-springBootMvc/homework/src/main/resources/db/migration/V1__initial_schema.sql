-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_seq start with 1 increment by 1;

create table client
(
    id   bigint not null primary key default nextval('client_seq'),
    name varchar(50)
);
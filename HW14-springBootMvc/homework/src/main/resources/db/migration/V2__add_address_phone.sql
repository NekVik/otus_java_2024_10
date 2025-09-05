create sequence address_seq start with 1 increment by 1;

create sequence phone_seq start with 1 increment by 1;

create table address (
    id bigint not null default nextval('address_seq'),
    street varchar(255),
    primary key (id)
);

create table phone (
    id bigint not null default nextval('phone_seq'),
    phone varchar(255),
    client_id bigint,
    ord int,
    primary key (id)
);

alter table client add address_id bigint;

alter table if exists client add constraint FK_ADDRES_ID foreign key (address_id) references address;

alter table if exists phone add constraint FK_CLIENT_ID foreign key (client_id) references client;


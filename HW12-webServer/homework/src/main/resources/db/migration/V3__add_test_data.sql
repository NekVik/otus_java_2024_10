delete from phone;

delete from client;

delete from address;

insert into address (id, street) values (1 , 'Санкт-Петербург, Кушелевская дор, дом № 1');
insert into address (id, street) values (2 , 'Москва, Бартеневская 12');

insert into client (id, name, address_id) values (1 , 'Иванов Иван Иванович', 1);
insert into client (id, name, address_id) values (2 , 'Петров Петр Петрович', 2);
insert into client (id, name, address_id) values (3 , 'Сидоров Иван Викторович', 1);
insert into client (id, name, address_id) values (4 , 'Кузнецов Кузьма Петрович', 2);

insert into phone (id, phone, client_id) values (1 , '89211234567', 1);
insert into phone (id, phone, client_id) values (2 , '89211234571', 1);
insert into phone (id, phone, client_id) values (3 , '89211234568', 2);
insert into phone (id, phone, client_id) values (4 , '89211234569', 3);
insert into phone (id, phone, client_id) values (5 , '89211234570', 4);

ALTER SEQUENCE address_seq RESTART WITH 3;

ALTER SEQUENCE client_SEQ RESTART WITH 5;

ALTER SEQUENCE phone_seq RESTART WITH 7;

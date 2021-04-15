drop database certificate;

create database if not exists certificate;

use certificate;

create table tag(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE, PRIMARY KEY (id));

create table gift_certificate(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE,
description VARCHAR(200), price INT, duration INT, create_date VARCHAR(50) NOT NULL,
last_update_date VARCHAR(50), PRIMARY KEY (id));

create table gift_certificate_tag(certificate INT NOT NULL, tag INT NOT NULL);

insert into tag (name) values ('aaa');
insert into tag (name) values ('bbb');
insert into tag (name) values ('ccc');

insert into gift_certificate (name, description, price, duration, create_date) values
('partName1', 'asdasa sada d asdasd', 10, 2, '2021-03-21T20:52:13.5213'),
('a2', 'asdasa sada d asdasd', 10, 2, '2021-03-24T00:00:13.5213'),
('a3', 'asdasa sada d asdasd', 10, 2, '2021-03-25T05:51:13.5213'),
('a4', 'asdasa sada d asdasd', 10, 2, '2021-03-29T06:52:13.5213');

insert into gift_certificate_tag (certificate, tag) values
(1, 1),
(2, 2),
(3, 3),
(4, 1),
(4, 2),
(4, 3);

select * from tag;
select * from gift_certificate;
select * from gift_certificate_tag;
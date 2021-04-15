drop database certificate;

create database if not exists certificate;

use certificate;

create table tag(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE, PRIMARY KEY (id));

create table gift_certificate(id INT AUTO_INCREMENT, name VARCHAR(50) NOT NULL UNIQUE,
description VARCHAR(200), price INT, duration INT, create_date VARCHAR(50) NOT NULL,
last_update_date VARCHAR(50), PRIMARY KEY (id));

create table gift_certificate_tag(certificate INT NOT NULL, tag INT NOT NULL);
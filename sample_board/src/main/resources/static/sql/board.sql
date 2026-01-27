
 create database springBootDB2;
 use springBootDB2; 

create table board(
id int auto_increment primary key,
title varchar(50) not null,
content varchar(300) not null,
writer varchar(20) not null,
createdAt datetime default now()
);
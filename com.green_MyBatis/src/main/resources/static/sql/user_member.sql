--  DB(스키마이름) springBootDB

create database springBootDB;
use springBootDB; 
-- 테이블 이름 : user_member
 create table user_member(
 no int auto_increment primary key,
 id varchar(20) not null unique,
 pw varchar(100) not null,
 mail varchar(50) not null,
 phone varchar(50) not null,
 reg_date datetime default now(), -- user의 등록일 
 mod_date datetime default now() -- user의 정보 수정날짜
 );

 create database springBootDB2;
 use springBootDB2; 

create table board(
id int auto_increment primary key,
title varchar(50) not null,
content varchar(300) not null,
writer varchar(20) not null,
createdAt datetime default now()
);

create table otboard(
num int auto_increment primary key,
writer varchar(20),
subject varchar(30),
writerPw varchar(20),
reg_date datetime default now(),
readcount int default 0,
content varchar(1000)
);


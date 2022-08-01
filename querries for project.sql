create database user ;

use user;

create table users(
Username varchar(100) not null unique,
Password varchar(100) not null,
email varchar(100) not null unique,
age int not null,
CNIC varchar(100) not null,
dateofbirth varchar(100) not null,
address varchar(200) not null,
Id int not Null primary key auto_increment
);

insert users(username,password,email,age,cnic,dateofbirth,address) values("admin","youcantguess","N/A",21,"N/A","N/A","N/A");


select * from users;


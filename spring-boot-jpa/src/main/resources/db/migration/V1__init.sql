drop table if exists account cascade;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;
create table account (id int8 not null, password varchar(255), username varchar(255), primary key (id));
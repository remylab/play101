# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table member (
  id                        bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint uq_member_username unique (username),
  constraint pk_member primary key (id))
;

create table member3 (
  id                        bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint uq_member3_username unique (username),
  constraint uq_member3_email unique (email),
  constraint pk_member3 primary key (id))
;

create sequence member_seq;

create sequence member3_seq;




# --- !Downs

drop table if exists member cascade;

drop table if exists member3 cascade;

drop sequence if exists member_seq;

drop sequence if exists member3_seq;


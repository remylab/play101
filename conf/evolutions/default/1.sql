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

create table member3todo (
  id                        bigint not null,
  member3_id                bigint not null,
  todo                      varchar(255),
  constraint pk_member3todo primary key (id))
;

create sequence member_seq;

create sequence member3_seq;

create sequence member3todo_seq;

alter table member3todo add constraint fk_member3todo_member3_1 foreign key (member3_id) references member3 (id);
create index ix_member3todo_member3_1 on member3todo (member3_id);



# --- !Downs

drop table if exists member cascade;

drop table if exists member3 cascade;

drop table if exists member3todo cascade;

drop sequence if exists member_seq;

drop sequence if exists member3_seq;

drop sequence if exists member3todo_seq;


/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2022/10/17 10:33:11                          */
/*==============================================================*/


drop table if exists Character;

drop table if exists Relationship_2;

drop table if exists Relationship_8;

drop table if exists data_dictionary;

drop table if exists grade;

drop table if exists operate_log;

drop table if exists operate_objection;

drop table if exists operation;

drop table if exists permission;

drop table if exists sign_log;

drop table if exists "user ID";

/*==============================================================*/
/* Table: Character                                             */
/*==============================================================*/
create table Character
(
   chara_code           char(1) not null,
   chara_name           varchar(20),
   primary key (chara_code)
);

/*==============================================================*/
/* Table: Relationship_2                                        */
/*==============================================================*/
create table Relationship_2
(
   chara_code           char(1) not null,
   p_id                 char(10) not null,
   primary key (chara_code, p_id)
);

/*==============================================================*/
/* Table: Relationship_8                                        */
/*==============================================================*/
create table Relationship_8
(
   grade_id             char(12) not null,
   use_id               numeric(10,0) not null,
   primary key (grade_id, use_id)
);

/*==============================================================*/
/* Table: data_dictionary                                       */
/*==============================================================*/
create table data_dictionary
(
   data_dictionart_id   char(3) not null,
   data_type            varchar(10) not null,
   data_value           varchar(10) not null,
   primary key (data_dictionart_id)
);

/*==============================================================*/
/* Table: grade                                                 */
/*==============================================================*/
create table grade
(
   grade_id             char(12) not null,
   gra_grade_id         char(12),
   lesson_num           char(10) not null,
   class_num            numeric(2,0) not null,
   成绩（百分制）              int not null,
   "绩点（4.3）"            float(2) not null,
   semester             numeric(5,0) not null,
   lesson_name          varchar(100),
   学分                   int,
   课程性质                 bool,
   primary key (grade_id)
);

/*==============================================================*/
/* Table: operate_log                                           */
/*==============================================================*/
create table operate_log
(
   opera_log_id         char(10) not null,
   opera_id             char(1) not null,
   opera_time           datetime not null,
   primary key (opera_log_id)
);

/*==============================================================*/
/* Table: operate_objection                                     */
/*==============================================================*/
create table operate_objection
(
   objection_id         char(5) not null,
   parent_objection_id  char(5),
   objection_name       varchar(20) not null,
   primary key (objection_id)
);

/*==============================================================*/
/* Table: operation                                             */
/*==============================================================*/
create table operation
(
   opera_id             char(1) not null,
   opera_name           varchar(20) not null,
   primary key (opera_id)
);

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   p_id                 char(10) not null,
   opera_id             char(1) not null,
   objection_id         char(5) not null,
   p_name               varchar(20) not null,
   primary key (p_id)
);

/*==============================================================*/
/* Table: sign_log                                              */
/*==============================================================*/
create table sign_log
(
   sign_log_id          char(10) not null,
   use_id               numeric(10,0) not null,
   sign_time            datetime not null,
   primary key (sign_log_id)
);

/*==============================================================*/
/* Table: "user ID"                                             */
/*==============================================================*/
create table "user ID"
(
   use_id               numeric(10,0) not null,
   chara_code           char(1) not null,
   password             varchar(16) not null,
   gender               bool not null,
   user_email           varchar(20),
   user_name            varchar(20),
   primary key (use_id)
);

alter table Relationship_2 add constraint FK_Relationship_2 foreign key (chara_code)
      references Character (chara_code) on delete restrict on update restrict;

alter table Relationship_2 add constraint FK_Relationship_3 foreign key (p_id)
      references permission (p_id) on delete restrict on update restrict;

alter table Relationship_8 add constraint FK_Relationship_10 foreign key (use_id)
      references "user ID" (use_id) on delete restrict on update restrict;

alter table Relationship_8 add constraint FK_Relationship_9 foreign key (grade_id)
      references grade (grade_id) on delete restrict on update restrict;

alter table grade add constraint FK_Relationship_11 foreign key (gra_grade_id)
      references grade (grade_id) on delete restrict on update restrict;

alter table operate_log add constraint FK_Relationship_6 foreign key (opera_id)
      references operation (opera_id) on delete restrict on update restrict;

alter table operate_objection add constraint FK_Relationship_7 foreign key (parent_objection_id)
      references operate_objection (objection_id) on delete restrict on update restrict;

alter table permission add constraint FK_Relationship_4 foreign key (opera_id)
      references operation (opera_id) on delete restrict on update restrict;

alter table permission add constraint FK_Relationship_5 foreign key (objection_id)
      references operate_objection (objection_id) on delete restrict on update restrict;

alter table sign_log add constraint FK_Relationship_8 foreign key (use_id)
      references "user ID" (use_id) on delete restrict on update restrict;

alter table "user ID" add constraint FK_Relationship_12 foreign key (chara_code)
      references Character (chara_code) on delete restrict on update restrict;


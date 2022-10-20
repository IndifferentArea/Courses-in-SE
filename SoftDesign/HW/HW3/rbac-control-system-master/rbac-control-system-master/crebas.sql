/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/10/23 13:30:22                          */
/*==============================================================*/


drop table if exists Table_6;

drop table if exists t_action;

drop table if exists t_class;

drop table if exists t_class_exam;

drop table if exists t_course;

drop table if exists t_exam;

drop table if exists t_object;

drop table if exists t_role;

drop table if exists t_score;

drop table if exists t_student;

drop table if exists t_teacher;

drop table if exists t_teacher_class;

drop table if exists t_user_info;

drop table if exists t_user_permission;

/*==============================================================*/
/* Table: Table_6                                               */
/*==============================================================*/
create table Table_6
(
   permission_id        int not null,
   obj_id               int not null,
   action_id            int,
   primary key (permission_id)
);

/*==============================================================*/
/* Table: t_action                                              */
/*==============================================================*/
create table t_action
(
   action_id            int not null,
   action_name          varchar(30) not null,
   primary key (action_id)
);

/*==============================================================*/
/* Table: t_class                                               */
/*==============================================================*/
create table t_class
(
   class_id             int not null,
   class_num            varchar(20) not null,
   primary key (class_id)
);

/*==============================================================*/
/* Table: t_class_exam                                          */
/*==============================================================*/
create table t_class_exam
(
   class_exam_id        int not null,
   class_id             int,
   exam_id              int,
   primary key (class_exam_id)
);

/*==============================================================*/
/* Table: t_course                                              */
/*==============================================================*/
create table t_course
(
   course_id            int not null,
   course_name          varchar(20) not null,
   primary key (course_id)
);

/*==============================================================*/
/* Table: t_exam                                                */
/*==============================================================*/
create table t_exam
(
   exam_id              int not null,
   teacher_id           int,
   course_id            int,
   exam_name            varchar(30) not null,
   exam_type            varchar(30) not null,
   exam_time            date not null,
   primary key (exam_id)
);

/*==============================================================*/
/* Table: t_object                                              */
/*==============================================================*/
create table t_object
(
   obj_id               int not null,
   obj_name             varchar(30) not null,
   primary key (obj_id)
);

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   role_id              int not null,
   role_name            varchar(20) not null,
   role_desc            varchar(100) not null comment '对于该角色大致功能的描述',
   primary key (role_id)
);

alter table t_role comment '用户在该系统中的角色,类似超级管理员,普通管理员,';

/*==============================================================*/
/* Table: t_score                                               */
/*==============================================================*/
create table t_score
(
   score_id             int not null,
   stu_id               int,
   exam_id              int,
   score                int not null,
   primary key (score_id)
);

/*==============================================================*/
/* Table: t_student                                             */
/*==============================================================*/
create table t_student
(
   stu_id               int not null,
   info_id              int,
   class_id             int,
   primary key (stu_id)
);

/*==============================================================*/
/* Table: t_teacher                                             */
/*==============================================================*/
create table t_teacher
(
   teacher_id           int not null,
   info_id              int,
   course_id            int,
   primary key (teacher_id)
);

/*==============================================================*/
/* Table: t_teacher_class                                       */
/*==============================================================*/
create table t_teacher_class
(
   id                   int not null,
   teacher_id           int,
   class_id             int,
   primary key (id)
);

/*==============================================================*/
/* Table: t_user_info                                           */
/*==============================================================*/
create table t_user_info
(
   info_id              int not null,
   role_id              int,
   passwd               varchar(30) not null,
   name                 varchar(20) comment '用户的真实姓名',
   u_gender             varchar(4) comment '用户性别,"男"or"女"',
   u_mail               varchar(30),
   primary key (info_id)
);

alter table t_user_info comment '描述用户基本信息';

/*==============================================================*/
/* Table: t_user_permission                                     */
/*==============================================================*/
create table t_user_permission
(
   user_per_id          int not null,
   permission_id        int,
   role_id              int,
   primary key (user_per_id)
);

alter table Table_6 add constraint FK_Reference_20 foreign key (action_id)
      references t_action (action_id) on delete restrict on update restrict;

alter table Table_6 add constraint FK_Reference_4 foreign key (obj_id)
      references t_object (obj_id) on delete restrict on update restrict;

alter table t_class_exam add constraint FK_Reference_16 foreign key (class_id)
      references t_class (class_id) on delete restrict on update restrict;

alter table t_class_exam add constraint FK_Reference_17 foreign key (exam_id)
      references t_exam (exam_id) on delete restrict on update restrict;

alter table t_exam add constraint FK_Reference_14 foreign key (teacher_id)
      references t_teacher (teacher_id) on delete restrict on update restrict;

alter table t_exam add constraint FK_Reference_15 foreign key (course_id)
      references t_course (course_id) on delete restrict on update restrict;

alter table t_score add constraint FK_Reference_18 foreign key (stu_id)
      references t_student (stu_id) on delete restrict on update restrict;

alter table t_score add constraint FK_Reference_19 foreign key (exam_id)
      references t_exam (exam_id) on delete restrict on update restrict;

alter table t_student add constraint FK_Reference_12 foreign key (class_id)
      references t_class (class_id) on delete restrict on update restrict;

alter table t_student add constraint FK_Reference_7 foreign key (info_id)
      references t_user_info (info_id) on delete restrict on update restrict;

alter table t_teacher add constraint FK_Reference_13 foreign key (course_id)
      references t_course (course_id) on delete restrict on update restrict;

alter table t_teacher add constraint FK_Reference_8 foreign key (info_id)
      references t_user_info (info_id) on delete restrict on update restrict;

alter table t_teacher_class add constraint FK_Reference_10 foreign key (teacher_id)
      references t_teacher (teacher_id) on delete restrict on update restrict;

alter table t_teacher_class add constraint FK_Reference_11 foreign key (class_id)
      references t_class (class_id) on delete restrict on update restrict;

alter table t_user_info add constraint FK_t_user_role foreign key (role_id)
      references t_role (role_id) on delete restrict on update restrict;

alter table t_user_permission add constraint FK_Reference_21 foreign key (permission_id)
      references Table_6 (permission_id) on delete restrict on update restrict;

alter table t_user_permission add constraint FK_Reference_22 foreign key (role_id)
      references t_role (role_id) on delete restrict on update restrict;


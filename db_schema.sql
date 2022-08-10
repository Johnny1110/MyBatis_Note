create table country
(
    id          int auto_increment
        primary key,
    countryname varchar(255) null,
    countrycode varchar(255) null
);

INSERT INTO mybatis.country (id, countryname, countrycode) VALUES (1, '台灣', 'TW');
INSERT INTO mybatis.country (id, countryname, countrycode) VALUES (2, '美國', 'US');
INSERT INTO mybatis.country (id, countryname, countrycode) VALUES (3, '俄羅斯', 'RU');
INSERT INTO mybatis.country (id, countryname, countrycode) VALUES (4, '英國', 'GB');
INSERT INTO mybatis.country (id, countryname, countrycode) VALUES (5, '法國', 'FR');
INSERT INTO mybatis.country (id, countryname, countrycode) VALUES (6, '中國', 'CN');

create table sys_dict
(
    id    bigint(32) auto_increment comment 'PK'
        primary key,
    code  varchar(64) not null comment '類別',
    name  varchar(64) not null comment '字典名',
    value varchar(64) not null comment '字典值'
);

INSERT INTO mybatis.sys_dict (id, code, name, value) VALUES (1, '性別', '男', '男');
INSERT INTO mybatis.sys_dict (id, code, name, value) VALUES (2, '性别', '女', '女');
INSERT INTO mybatis.sys_dict (id, code, name, value) VALUES (3, '季度', '第一季度', '1');
INSERT INTO mybatis.sys_dict (id, code, name, value) VALUES (4, '季度', '第二季度', '2');
INSERT INTO mybatis.sys_dict (id, code, name, value) VALUES (5, '季度', '第三季度', '3');
INSERT INTO mybatis.sys_dict (id, code, name, value) VALUES (6, '季度', '第四季度', '4');


create table sys_privilege
(
    id             bigint auto_increment comment '權限 ID'
        primary key,
    privilege_name varchar(50)  null comment '權限名稱',
    privilege_url  varchar(200) null comment '權限 URL'
)
    comment '權限表';

INSERT INTO mybatis.sys_privilege (id, privilege_name, privilege_url) VALUES (1, '使用者管理', '/users');
INSERT INTO mybatis.sys_privilege (id, privilege_name, privilege_url) VALUES (2, '角色管理', '/roles');
INSERT INTO mybatis.sys_privilege (id, privilege_name, privilege_url) VALUES (3, '系統日誌', '/logs');
INSERT INTO mybatis.sys_privilege (id, privilege_name, privilege_url) VALUES (4, '人員維護', '/persons');
INSERT INTO mybatis.sys_privilege (id, privilege_name, privilege_url) VALUES (5, '單位維護', '/companies');


create table sys_role
(
    id          bigint auto_increment comment '角色 ID'
        primary key,
    role_name   varchar(50) null comment '角色名',
    enabled     int         null comment '有效標誌',
    create_by   bigint      null comment '創建人 ID',
    create_time datetime    null comment '創建時間'
)
    comment '角色表';

INSERT INTO mybatis.sys_role (id, role_name, enabled, create_by, create_time) VALUES (1, '管理員', 1, 1, '2022-04-01 17:02:14');
INSERT INTO mybatis.sys_role (id, role_name, enabled, create_by, create_time) VALUES (2, '普通使用者', 1, 1, '2022-04-01 17:02:34');


create table sys_role_privilege
(
    role_id      bigint null comment '角色 ID',
    privilege_id bigint null comment '權限 ID',
    constraint sys_role_privilege_sys_privilege_id_fk
        foreign key (privilege_id) references sys_privilege (id),
    constraint sys_role_privilege_sys_role_id_fk
        foreign key (role_id) references sys_role (id)
)
    comment '角色權限關聯表';

INSERT INTO mybatis.sys_role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO mybatis.sys_role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO mybatis.sys_role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO mybatis.sys_role_privilege (role_id, privilege_id) VALUES (2, 4);
INSERT INTO mybatis.sys_role_privilege (role_id, privilege_id) VALUES (2, 5);


create table sys_user
(
    id            bigint auto_increment comment '使用者 ID'
        primary key,
    user_name     varchar(50)                           null comment '使用者名稱',
    user_password varchar(50)                           null comment '使用者密碼',
    user_email    varchar(50) default 'test@mybatis.tk' null comment '信箱',
    user_info     text                                  null comment '簡介',
    head_img      blob                                  null comment '頭像',
    create_time   datetime                              null comment '創建時間'
)
    comment '使用者表';

INSERT INTO mybatis.sys_user (id, user_name, user_password, user_email, user_info, head_img, create_time) VALUES (1, 'admin', '123456', 'Jarvan1110@gmail.com', '管理員', 0x1231231230, '2022-06-07 01:11:12');
INSERT INTO mybatis.sys_user (id, user_name, user_password, user_email, user_info, head_img, create_time) VALUES (1001, 'test', '123456', 'johnnywang@gashpoint.com', '一般使用者', 0x1231231230, '2022-06-07 00:00:00');


create table sys_user_role
(
    user_id bigint null comment '使用者 ID',
    role_id bigint null comment '角色 ID',
    constraint sys_user_role_sys_role_id_fk
        foreign key (role_id) references sys_role (id),
    constraint sys_user_role_sys_user_id_fk
        foreign key (user_id) references sys_user (id)
)
    comment '使用者角色關聯表';

INSERT INTO mybatis.sys_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO mybatis.sys_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO mybatis.sys_user_role (user_id, role_id) VALUES (1001, 2);


create table `user info`
(
    id int not null
        primary key
);


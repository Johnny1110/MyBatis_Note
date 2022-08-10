# Mybatis XML 基本使用方法

<br>

---

<br>

## 建立 Table

<br>

採用 RBAC（Role-Based Access Control，基於角色訪問控制），設計一套權限控制結構：

<br>

![1](imgs/1.jpg)

<br>

db schema：

```sql
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
```


<br>

一個使用者可以同時傭有多個角色身分，一個角色身分可以有多個權限。

權限可以對某個資源進行某些操作 （增 刪 改 查）。

<br>
<br>
<br>
<br>

## 根據 Table 建立實體類 Entity

<br>

範例都放在 Model package 里。

![2](imgs/2.jpg)

<br>
<br>
<br>
<br>

## 配置 Mapper

<br>

建立 Mapper interface：

<br>

![3](imgs/3.jpg)

<br>

建立 Mapper xml：

<br>

![4](imgs/4.jpg)

<br>
<br>
<br>
<br>


## 配置 mybatis-config.xml

<br>

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--    配置 log4j 作為輸出日誌-->
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
    <!--配置常用 package 別名-->
    <typeAliases>
        <package name="com.frizo.lab.mybatis.model"/>
    </typeAliases>
    <!--配置資料庫連線-->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
                <property name="" value=""/>
            </transactionManager>
            <dataSource type="UNPOOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?serverTimezone=UTC&amp;characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="Jarvan1110"/>
            </dataSource>
        </environment>
    </environments>

    <!--    MyBatis 的 SQL 語句與映射配置文件-->
    <mappers>
        <package name="com.frizo.lab.mybatis.mapper"/>
    </mappers>
</configuration>
```

<br>
<br>
<br>
<br>

## select 用法

<br>

寫在 UserMapper.xml 里，可以參考。

```xml
    <!-- Entity 與 DB_Table 欄位對應設定 -->
	<resultMap id="userMap" type="com.frizo.lab.mybatis.model.SysUser">
		<id property="id" column="id"/> <!--必填欄位-->
		<result property="userName" column="user_name"/>
		<result property="userPassword" column="user_password"/>
		<result property="userEmail" column="user_email"/>
		<result property="userInfo" column="user_info"/>
		<result property="headImg" column="head_img" jdbcType="BLOB"/>
		<result property="createTime" column="create_time" jdbcType="TIMESTAMP"/>
	</resultMap>

	<!-- <select> 映射查詢語句 -->
	<!-- resultMap 設定返回值的類型與映射關係 -->
	<!-- #{id} 是傳入參數名稱 -->
	<select id="selectById" resultMap="userMap">
		select * from sys_user where id = #{id}
	</select>

	<!-- resultType 指定返回類型 -->
	<select id="selectAll" resultType="com.frizo.lab.mybatis.model.SysUser">
		select id,
			user_name,
			user_password,
			user_email,
			user_info,
			head_img,
			create_time
		from sys_user
	</select>
```


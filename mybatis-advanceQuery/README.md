# Mybatis 高級查詢

<br>

---

<br>

## 高級結果映射

<br>

### 一對一映射

<br>

SysUser.java

```java
private Long id;

private String userName;

...

// 添加
private SysRole role;

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
```

<br>

SysUserMapper.java

```java
SysUser selectUserAndRoleById(Long userId);
```

<br>

SysUserMapper.xml

```xml
  <select id="selectUserAndRoleById" resultType="com.frizo.lab.mybatis.model.SysUser">
    select su.id,
           su.user_name userName,
           su.user_password userPassword,
           su.user_email userEmail,
           su.user_info userInfo,
           su.head_img headImg,
           su.create_time createTime,
           sr.id "role.id",
           sr.role_name "role.roleName",
           sr.enabled "role.enabled",
           sr.create_by "role.createBy",
           sr.create_time "role.createTime"
    from sys_user as su
    inner join sys_user_role sur on su.id = sur.user_id
    inner join sys_role sr on sur.role_id = sr.id
    where su.id = #{userId}
  </select>
```

<br>
<br>
<br>
<br>

### 改用 resultMap 方式配置一對一映射

<br>

resultMap：

<br>

```xml
<resultMap id="userRoleMap" type="com.frizo.lab.mybatis.model.SysUser">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="user_name" jdbcType="VARCHAR" property="userName" />
    <result column="user_password" jdbcType="VARCHAR" property="userPassword" />
    <result column="user_email" jdbcType="VARCHAR" property="userEmail" />
    <result column="create_time" jdbcType="TIMESTAMP" property="createTime" />
    <result column="user_info" jdbcType="LONGVARCHAR" property="userInfo" />
    <result column="head_img" jdbcType="LONGVARBINARY" property="headImg" />
    <!-- role 相關 -->
    <result column="role_id" jdbcType="BIGINT" property="role.id" />
    <result column="role_name" jdbcType="VARCHAR" property="role.roleName" />
    <result column="enabled" jdbcType="INTEGER" property="role.enabled" />
    <result column="create_by" jdbcType="BIGINT" property="role.createBy" />
    <result column="role_create_time" jdbcType="TIMESTAMP" property="role.createTime" />
  </resultMap>
```

<br>

select：

<br>

```xml
<select id="selectUserAndRoleById2" resultMap="userRoleMap">
    select su.id,
           su.user_name,
           su.user_password,
           su.user_email,
           su.user_info,
           su.head_img,
           su.create_time,
           sr.id role_id,
           sr.role_name ,
           sr.enabled,
           sr.create_by,
           sr.create_time role_create_time
    from sys_user as su
           inner join sys_user_role sur on su.id = sur.user_id
           inner join sys_role sr on sur.role_id = sr.id
    where su.id = #{userId}
  </select>
```

<br>

也可以用繼承：

<br>

```xml
<resultMap id="userRoleMap2" extends="BaseResultMap" type="com.frizo.lab.mybatis.model.SysUser">
    <result column="role_id" jdbcType="BIGINT" property="role.id" />
    <result column="role_name" jdbcType="VARCHAR" property="role.roleName" />
    <result column="enabled" jdbcType="INTEGER" property="role.enabled" />
    <result column="create_by" jdbcType="BIGINT" property="role.createBy" />
    <result column="role_create_time" jdbcType="TIMESTAMP" property="role.createTime" />
  </resultMap>
```

<br>

### 用 resultMap 搭配 association 方式配置一對一映射

<br>

```xml
<resultMap id="userRoleMap3" extends="BaseResultMap" type="com.frizo.lab.mybatis.model.SysUser">
    <association property="role" columnPrefix="role_" javaType="com.frizo.lab.mybatis.model.SysRole">
      <result column="id" jdbcType="BIGINT" property="id" />
      <result column="role_name" jdbcType="VARCHAR" property="roleName" />
      <result column="enabled" jdbcType="INTEGER" property="enabled" />
      <result column="create_by" jdbcType="BIGINT" property="createBy" />
      <result column="create_time" jdbcType="TIMESTAMP" property="createTime" />
    </association>
</resultMap>
```

<br>

也可以用繼承 + association + 別的 Mapper.xml 內的 resultMap：

<br>

__建議就用這個了__

<br>


```xml
<resultMap id="userRoleMap5" extends="BaseResultMap" type="com.frizo.lab.mybatis.model.SysUser">
    <association property="role" columnPrefix="role_" resultMap="com.frizo.lab.mybatis.mapper.SysRoleMapper.BaseResultMap"/>
  </resultMap>
```

<br>

```xml
<select id="selectUserAndRoleById2" resultMap="userRoleMap5">
    select su.id,
           su.user_name,
           su.user_password,
           su.user_email,
           su.user_info,
           su.head_img,
           su.create_time,
           sr.id role_id,
           sr.role_name role_role_name,
           sr.enabled role_enabled,
           sr.create_by role_create_by,
           sr.create_time role_create_time
    from sys_user as su
           inner join sys_user_role sur on su.id = sur.user_id
           inner join sys_role sr on sur.role_id = sr.id
    where su.id = #{userId}
  </select>
```
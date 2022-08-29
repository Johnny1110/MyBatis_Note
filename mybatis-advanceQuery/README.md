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
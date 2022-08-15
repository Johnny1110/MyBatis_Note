# Mybatis Annotation 基本使用方法

<br>

---

<br>

資料庫沿用 mybatis-xml 使用的那一套。

<br>

## `@select` 註解

<br>

```java
@Select({"select id, role_name, enabled, create_by, create_time", 
            "from sys_role",
            "where id = #{id}"
    })
    SysRole selectById(Long id);
```

<br>

其他都略吧，跟 xml 基本一樣，只是換一種配置方式而已

<br>

細節都寫在 __RoleMapper__ 裡面，有需要再去看。

<br>

## Provider 方式

```java
public class PrivilegeProvider {

    public String selectById(final Long id){
        return new SQL(){
            {
                SELECT("id, privilege_name, privilege_url");
                FROM("sys_privilege");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
```

<br>

```java
public interface PrivilegeMapper {

    // method 方法返回的返回值必須是 String
    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(Long id);

}
```


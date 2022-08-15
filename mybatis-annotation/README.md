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

使用 resultMap 



package com.frizo.lab.mybatis.mapper;


import com.frizo.lab.mybatis.model.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    List<SysRole> selectRolesByUserId(Long userId);

    @Select({"select id, role_name, enabled, create_by, create_time",
            "from sys_role",
            "where id = #{id}"
    })
    SysRole selectById(Long id);

    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select({"select id, role_name, enabled, create_by, create_time",
            "from sys_role",
            "where id = #{id}"
    })
    SysRole selectById2(Long id);

    @ResultMap("roleResultMap") // 借用上面的 Results Ｍybatis 3.3.1 以上版本才可以這樣用
    @Select("select * from sys_role")
    List<SysRole> selectAll();

    @Insert({"insert into sys_role(id, role_name, enabled, create_by, create_time)",
            "values(#{id}, #{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType=TIMESTAMP})"
    })
    int insert(SysRole role);

    @Insert({"insert into sys_role(role_name, enabled, create_by, create_time)",
            "values(#{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertReturnKey(SysRole role);

    @Insert({"insert into sys_role(role_name, enabled, create_by, create_time)",
            "values(#{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Long.class, before = false)
    int insertReturnKey2(SysRole role);

    @Update({"update sys_role",
            "set role_name = #{roleName},",
            "enabled = #{enabled},",
            "create_by = #{createBy},",
            "create_time = #{createTime, jdbcType=TIMESTAMP}",
            "where id = #{id}"
    })
    int update(SysRole role);

    @Delete("delete from sys_role where id = #{id}")
    int deleteById(Long id);

}

package com.frizo.lab.mybatis.mapper;


import com.frizo.lab.mybatis.model.SysRole;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

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

}

package com.frizo.lab.mybatis.mapper;


import com.frizo.lab.mybatis.model.SysRole;

import java.util.List;

public interface RoleMapper {

    List<SysRole> selectRolesByUserId(Long userId);

}

package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysRole;
import com.frizo.lab.mybatis.model.SysUser;


import java.util.List;

public interface UserMapper {

    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRolesByUserId(Long userId);

    int insert(SysUser sysUser);

    int insertReturnKey(SysUser sysUser);

    int insertWithSelectKey(SysUser sysUser);

    int update(SysUser sysUser);

    int delete(SysUser sysUser);

}

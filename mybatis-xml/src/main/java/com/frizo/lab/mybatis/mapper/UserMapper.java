package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysUser;

import java.util.List;

public interface UserMapper {

    SysUser selectById(Long id);

    List<SysUser> selectAll();

}

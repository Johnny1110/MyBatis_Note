package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysRole;
import com.frizo.lab.mybatis.model.SysUser;
import org.apache.ibatis.annotations.Param;


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

    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId,@Param("enabled") Integer enabled);

    List<SysUser> selectByUser(SysUser sysUser);

    // 選擇性更新以 Selective 為後綴
    int updateByIdSelective(SysUser sysUser);
}

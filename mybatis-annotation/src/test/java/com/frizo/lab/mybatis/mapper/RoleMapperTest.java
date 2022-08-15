package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysRole;
import com.frizo.lab.mybatis.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RoleMapperTest extends BaseMapperTest {


    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            List<SysRole> roles = roleMapper.selectRolesByUserId(1L);
            roles.forEach(role -> {
                System.out.println(role.getRoleName());
                System.out.println(role.isEnabled());
            });
        }
    }

    @Test
    public void testSelectById(){
        try(SqlSession session = getSqlSession()){
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理員", role.getRoleName());
        }
    }

}

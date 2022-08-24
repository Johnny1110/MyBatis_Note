package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysRole;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
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

    @Test
    public void testSelectById2(){
        try(SqlSession session = getSqlSession()){
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById2(1L);
            Assert.assertNotNull(role);
            Assert.assertEquals("管理員", role.getRoleName());
        }
    }

    @Test
    public void testSelectAll(){
        try(SqlSession session = getSqlSession()){
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            List<SysRole> roles = roleMapper.selectAll();
            Assert.assertNotNull(roles);
            Assert.assertTrue(roles.size() > 0);
        }
    }

    @Test
    public void testInsertReturnKey2(){
        try(SqlSession session = getSqlSession()){
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            SysRole role = new SysRole();
            role.setRoleName("訪客");
            role.setEnabled(true);
            role.setCreateBy("1");
            role.setCreateTime(new Date());
            int result = roleMapper.insertReturnKey2(role);
            Assert.assertEquals(1, result);
            System.out.println("key: " + role.getId());
            session.rollback();
        }
    }

    @Test
    public void testDeleteById(){
        try(SqlSession session = getSqlSession()){
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            int result = roleMapper.deleteById(2L);
            Assert.assertEquals(1, result);
            session.rollback();
        }
    }

    @Test
    public void testUpdateById(){
        try(SqlSession session = getSqlSession()){
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            SysRole role = roleMapper.selectById(2L);
            role.setEnabled(false);
            int result = roleMapper.update(role);
            Assert.assertEquals(1, result);
            session.rollback();
        }
    }
}

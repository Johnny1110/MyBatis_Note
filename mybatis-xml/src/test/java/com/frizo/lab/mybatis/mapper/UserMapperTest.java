package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysRole;
import com.frizo.lab.mybatis.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<SysUser> users = userMapper.selectAll();
            Assert.assertNotNull(users);
            Assert.assertTrue(users.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<SysRole> roles = userMapper.selectRolesByUserId(1L);
            roles.forEach(role -> {
                System.out.println("role_name: " + role.getRoleName());
                System.out.println("enable: " + role.isEnabled());
                System.out.println("user_name: " + role.getUser().getUserName());
                System.out.println("user_email: " + role.getUser().getUserEmail());
            });
        }
    }

    @Test
    public void testInsert() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test123");
            user.setUserPassword("test123");
            user.setUserEmail("test@mybatis.org");
            user.setUserInfo("test info@");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insert(user); // result 是SQL 執行影響的行數
            Assert.assertEquals(1, result); // Id 不回寫，所以為 null
            Assert.assertNull(user.getId());
            session.commit(); // 不自己 commit() session 不會幫忙做
        }
    }

    @Test
    public void testInsertReturnKey() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1234");
            user.setUserPassword("test1234");
            user.setUserEmail("test4@mybatis.org");
            user.setUserInfo("test4 info@");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insertReturnKey(user); // result 是SQL 執行影響的行數
            Assert.assertEquals(1, result);
            Assert.assertNotNull(user.getId()); // Id 回寫，所以不為 null
            session.commit(); // 不自己 commit() session 不會幫忙做
        }
    }

    @Test
    public void testInsertWithSelectKey() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1234");
            user.setUserPassword("test1234");
            user.setUserEmail("test4@mybatis.org");
            user.setUserInfo("test4 info@");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insertWithSelectKey(user); // result 是SQL 執行影響的行數
            Assert.assertEquals(1, result);
            Assert.assertNotNull(user.getId()); // Id 回寫，所以不為 null
            session.commit(); // 不自己 commit() session 不會幫忙做
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            user.setUserName("admin_test");
            int result = userMapper.update(user);
            Assert.assertEquals(1, result);
            session.commit();
        }
    }

    @Test
    public void testDelete() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1046L);
            int result = userMapper.delete(user);
            Assert.assertEquals(1, result);
            session.rollback();
        }
    }

}

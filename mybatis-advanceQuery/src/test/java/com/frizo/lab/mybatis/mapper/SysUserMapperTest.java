package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

public class SysUserMapperTest extends BaseMapperTest{

    @Test
    public void testSelectUserAndRoleById(){
        try(SqlSession sqlSession = getSqlSession()){
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUser user = sysUserMapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getRole());
        }
    }

}

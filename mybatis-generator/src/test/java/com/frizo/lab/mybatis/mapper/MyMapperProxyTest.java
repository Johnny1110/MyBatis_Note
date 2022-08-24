package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.List;

public class MyMapperProxyTest extends BaseMapperTest {

    @Test
    public void testInvocation(){
        try(SqlSession sqlSession = getSqlSession()){
            MyMapperProxy<UserMapper> mapperProxy = new MyMapperProxy<>(UserMapper.class, sqlSession);
            UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{UserMapper.class},
                    mapperProxy);
            List<SysUser> users = userMapper.selectAll();
            Assert.assertTrue(users.size() > 0);
        }
    }

}

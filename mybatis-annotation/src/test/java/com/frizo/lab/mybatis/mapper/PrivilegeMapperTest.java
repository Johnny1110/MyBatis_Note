package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.SysPrivilege;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;


public class PrivilegeMapperTest extends BaseMapperTest{

    @Test
    public void testSelectById(){
        try(SqlSession session = getSqlSession()){
            PrivilegeMapper privilegeMapper = session.getMapper(PrivilegeMapper.class);
            SysPrivilege privilege = privilegeMapper.selectById(1L);
            Assert.assertNotNull(privilege);
            Assert.assertEquals("使用者管理", privilege.getPrivilegeName());
        }
    }

}

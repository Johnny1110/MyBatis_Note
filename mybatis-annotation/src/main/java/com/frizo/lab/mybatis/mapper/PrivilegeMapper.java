package com.frizo.lab.mybatis.mapper;


import com.frizo.lab.mybatis.model.SysPrivilege;
import org.apache.ibatis.annotations.SelectProvider;

public interface PrivilegeMapper {

    // method 方法返回的返回值必須是 String
    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(Long id);

}

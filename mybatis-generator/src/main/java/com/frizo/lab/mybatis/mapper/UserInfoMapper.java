package com.frizo.lab.mybatis.mapper;

import com.frizo.lab.mybatis.model.UserInfo;
import java.util.List;

public interface UserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user info
     *
     * @mbggenerated
     */
    int insert(UserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user info
     *
     * @mbggenerated
     */
    List<UserInfo> selectAll();
}
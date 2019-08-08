package com.baizhi.dao;

import com.baizhi.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component("adminDao")
public interface AdminDao {
    public Admin login(@Param("username") String username, @Param("password") String password);
}

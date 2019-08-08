package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import com.baizhi.entity.UserMonth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component("userDao")
public interface UserDao {
    public List<User> showAll();
    public List<User> showAllUser(@Param("page") Integer page, @Param("rows") Integer rows);
    public void add(User user);
    public void del(String id);
    public void update(User user);
    public User selectOne(String id);
    public   Integer  totalcount();
    public List<UserLocation> selectlocaltion(String sex);
    public List<UserMonth> selectmonth(@Param("month") String month, @Param("sex") String sex);

}

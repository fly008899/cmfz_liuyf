package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import com.baizhi.entity.UserMonth;

import java.util.List;

public interface UserService {
    public List<User> showAll();
    public List<User> showAllUser(Integer page, Integer rows);
    public Integer  totalCount();
    public String add(User user);
    public void del(String id);
    public void update(User user);
    public User selectOne(String id);
    public List<UserLocation> selectlocaltion(String sex);
    public List<UserMonth> selectmonth(String month,String sex);

}

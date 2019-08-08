package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserLocation;
import com.baizhi.entity.UserMonth;
import com.baizhi.util.Md5Utils;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<User> showAll() {
        List<User> list = userDao.showAll();
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<User> showAllUser(Integer page, Integer rows) {
        List<User> users = userDao.showAllUser(page, rows);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalCount() {
        Integer integer = userDao.totalcount();
        return integer;
    }

    @Override
    public String add(User user) {
        user.setId(UUIDUtil.getUUID());
        user.setStatus("未激活");
        user.setCreat_time(new Date());
        user.setSign("这个人很懒，什么都没留下");
        String salt = Md5Utils.getSalt(3);
        String password=user.getPassword()+salt;
        String pass = Md5Utils.getMd5Code(password);
        user.setPassword(pass);
        user.setSalt(salt);
        userDao.add(user);
        return user.getId();
    }

    @Override
    public void del(String id) {
        userDao.del(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public User selectOne(String id) {
        User user = userDao.selectOne(id);
        return user;
    }

    @Override
    public List<UserLocation> selectlocaltion(String sex) {
        List<UserLocation> list = userDao.selectlocaltion(sex);
        return list;
    }

    @Override
    public List<UserMonth> selectmonth(String month, String sex) {
        List<UserMonth> list = userDao.selectmonth(month, sex);
        return list;
    }
}

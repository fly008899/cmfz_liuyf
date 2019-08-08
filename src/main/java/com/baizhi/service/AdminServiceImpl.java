package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private HttpSession session;
    @Autowired
    private AdminDao adminDao;


    @Override
    public Admin login(String username, String password, String Code) {
        Admin admin = adminDao.login(username, password);
        Object code = session.getAttribute("code");
        if(!code.equals(Code)) throw new RuntimeException("验证码错误");
        if(admin!=null) session.setAttribute("admin",admin);
        if(admin==null) throw new RuntimeException("用户名或密码错误");
        return admin;
    }
}

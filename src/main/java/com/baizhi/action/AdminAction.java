package com.baizhi.action;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    private AdminService adminService;
    @Autowired
    private HttpSession session;


    @RequestMapping("/login")
    public String login(Model model, String username, String password, String Code) throws Exception {
        try {
            Admin admin = adminService.login(username, password, Code);
            session.setAttribute("admin",admin);
            return "redirect:/main/main.jsp";
        } catch (Exception e) {
            String message = e.getMessage();
            session.setAttribute("message",message);
            return "redirect:/login/login.jsp";
        }
    }

    @RequestMapping("/getCode")
    public String getImageCode(HttpServletResponse response) throws Exception {
        String code = SecurityCode.getSecurityCode(); //通过工具类获取验证码
        session.setAttribute("code", code); //在session中放一份验证码
        BufferedImage image = SecurityImage.createImage(code); //给验证码通过工具类获取图片流
        ServletOutputStream out = null;//输出流为null
        try {
            out = response.getOutputStream();//通过响应获取输出流
            ImageIO.write(image, "png", out);//调用图片流的写方法，通过输出流写出图片
        } catch (Exception e) {

        }
        return null;
    }
}

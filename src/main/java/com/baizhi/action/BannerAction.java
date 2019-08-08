package com.baizhi.action;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/banner")
public class BannerAction {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String,Object> showAll(Integer page, Integer rows){
        //当前页的数据
        List<Banner> list = bannerService.showAll(page,rows);
        //查询总条数
        Integer totalCount = bannerService.totalCount();
        Map<String,Object> maps=new HashMap<String,Object>();
        //当前页号
        maps.put("page",page);
        //总条数
        maps.put("records",totalCount);
        //总页数
        Integer  pageCount=0;
        if (totalCount%rows==0){
            pageCount=totalCount/rows;
        }else {
            pageCount=totalCount/rows+1;
        }
        maps.put("total",pageCount);
        //每页具体的数据
        maps.put("rows",list);
        return   maps;
    }
    @RequestMapping("/updateStatus")
    @ResponseBody
    public HashMap<String, Object> updateStatus(String id,String status){
        Banner banner = bannerService.selectOne(id);
        if(banner.getStatus().equals("未激活")){
            banner.setStatus("激活");
        }else{
            banner.setStatus("未激活");
        }
        bannerService.update(banner);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success","200");
        map.put("message","修改成功!");
        return map;
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add( Banner banner, String oper){
        String uuid = null;
        //执行添加方法
        if(oper.equals("add")){
            uuid = bannerService.add(banner);
        }
        //执行修改方法
        if(oper.equals("edit")){
            if(banner.getPic_path()==""){
                Banner banner1 = bannerService.selectOne(banner.getId());
                banner.setPic_path(banner1.getPic_path());
            }
            bannerService.edit(banner);
        }
        //执行删除方法
        if(oper.equals("del")){
            bannerService.del(banner.getId());
        }
        return uuid;
    }
    @RequestMapping("/uploadBanner")
    public void uploadBanner(MultipartFile pic_path, String id, HttpServletRequest request){
        //获取要上传文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        //获取文件夹
        File file = new File(realPath);
        //创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        //获取上传文件的名字
        String filename = pic_path.getOriginalFilename();
        String name = new Date().getTime()+"_"+filename;
        //文件上传
        try {
            pic_path.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Banner banner = new Banner();
        banner.setId(id);
        banner.setPic_path(name);
        //做修改,修改默认的图片路径
        bannerService.update(banner);
    }

}

package com.baizhi.action;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
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
@RequestMapping("/article")
public class ArticleAction {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/showAll")
    @ResponseBody
    public Map<String, Object> showAll(Integer page, Integer rows) {
        //当前页的数据
        List<Article> articles = articleService.showAllArticle(page, rows);
        //查询总条数
        Integer totalCount = articleService.totalCount();
        Map<String, Object> maps = new HashMap<String, Object>();
        //当前页号
        maps.put("page", page);
        //总条数
        maps.put("records", totalCount);
        //总页数
        Integer pageCount = 0;
        if (totalCount % rows == 0) {
            pageCount = totalCount / rows;
        } else {
            pageCount = totalCount / rows + 1;
        }
        maps.put("total", pageCount);
        //每页具体的数据
        maps.put("rows", articles);
        return maps;
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    public HashMap<String, Object> updateStatus(String id, String status) {
        Article article = articleService.selectOne(id);
        if (article.getStatus().equals("未激活")) {
            article.setStatus("激活");
        } else {
            article.setStatus("未激活");
        }
        articleService.update(article);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "200");
        map.put("message", "修改成功!");
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public String add(Article articler, String oper) {
        String uuid = null;
        //执行添加方法
        if (oper.equals("add")) {
            uuid = articleService.add(articler);
        }
        //执行修改方法
        if (oper.equals("edit")) {
            if (articler.getPicture() == "") {
                articler.setPicture(null);
            }
            articleService.update(articler);
        }
        //执行删除方法
        if (oper.equals("del")) {
            articleService.del(articler.getId());
        }
        return uuid;
    }

    @RequestMapping("/uploadPicture")
    public void uploadBanner(MultipartFile picture, String id, HttpServletRequest request) {
        //获取要上传文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        //获取文件夹
        File file = new File(realPath);
        //创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        //获取上传文件的名字
        String filename = picture.getOriginalFilename();
        String name = new Date().getTime() + "_" + filename;
        //文件上传
        try {
            picture.transferTo(new File(realPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Article article = new Article();
        article.setId(id);
        article.setPicture(name);
        System.out.println(article);
        //做修改,修改默认的图片路径
        articleService.update(article);
    }

    @RequestMapping("/update")
    @ResponseBody
    public HashMap<String, Object> updateArticle(Article article, String ArticleId) {
        Article one = articleService.selectOne(ArticleId);
        one.setContent(article.getContent());
        articleService.update(one);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", "200");
        map.put("message", "编辑文章成功!");
        return map;
    }
}

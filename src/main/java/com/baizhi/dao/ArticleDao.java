package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component("articleDao")
public interface ArticleDao {
    public List<Article> showAllArticle(@Param("page") Integer page, @Param("rows") Integer rows);
    public void add(Article article);
    public void del(String id);
    public void update(Article article);
    public Article selectOne(String id);
    public   Integer  totalcount();
}

package com.baizhi.service;

import com.baizhi.entity.Article;
import java.util.List;

public interface ArticleService {
    public List<Article> showAllArticle(Integer page, Integer rows);
    public Integer  totalCount();
    public String add(Article article);
    public void del(String id);
    public void update(Article article);
    public Article selectOne(String id);
}

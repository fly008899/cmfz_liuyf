package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> showAllArticle(Integer page, Integer rows) {
        List<Article> articles = articleDao.showAllArticle(page, rows);
        return articles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalCount() {
        Integer integer = articleDao.totalcount();
        return integer;
    }

    @Override
    public String add(Article article) {
        article.setId(UUIDUtil.getUUID());
        article.setStatus("未激活");
        article.setCreat_time(new Date());
        article.setContent("--待添加--");
        article.setGuru_id("公共文章");
        articleDao.add(article);
        return article.getId();
    }

    @Override
    public void del(String id) {
        articleDao.del(id);
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Article selectOne(String id) {
        Article article = articleDao.selectOne(id);
        return article;
    }
}

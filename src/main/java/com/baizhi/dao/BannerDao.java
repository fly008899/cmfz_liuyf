package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component("bannerDao")
public interface BannerDao {
    public List<Banner> showAll(@Param("page") Integer page, @Param("rows") Integer rows);
    public void add(Banner banner);
    public void del(String id);
    public void update(Banner banner);
    public void edit(Banner banner);
    public   Integer  totalcount();
    public Banner selectOne(String id);
}

package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;

public interface BannerService {
    public List<Banner> showAll(Integer page, Integer rows);
    public Integer  totalCount();
    public String add(Banner banner);
    public void del(String id);
    public void update(Banner banner);
    public void edit(Banner banner);
    public Banner selectOne(String id);
}

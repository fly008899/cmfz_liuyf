package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> showAll(Integer page, Integer rows) {
        List<Banner> list = bannerDao.showAll(page,rows);
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalCount() {
        Integer totalcount = bannerDao.totalcount();
        return  totalcount;
    }

    @Override
    public String add(Banner banner) {
        banner.setId(UUIDUtil.getUUID());
        banner.setStatus("未激活");
        banner.setCreat_time(new Date());
        bannerDao.add(banner);
        //必须返回uuid，用此id修改路径
        return banner.getId();
    }

    @Override
    public void del(String id) {
        bannerDao.del(id);
    }

    @Override
    public void update(Banner banner) {
        bannerDao.update(banner);
    }
    @Override
    public void edit(Banner banner) {
        bannerDao.edit(banner);
    }

    @Override
    public Banner selectOne(String id) {
        Banner banner = bannerDao.selectOne(id);
        return banner;
    }
}

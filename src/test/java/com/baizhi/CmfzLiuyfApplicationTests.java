package com.baizhi;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.*;
import com.baizhi.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzLiuyfApplicationTests {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private UserService UserService;
    @Autowired
    private UserDao userDao;

    @Test
    public void test2() {
        bannerDao.del("2");
    }

    @Test
    public void test3() {
        bannerDao.add(new Banner("2","测试2","asd","冻结",new Date(),"测试"));
    }
    @Test
    public void test4() {
        bannerDao.update(new Banner("2","测试","11111","正常",new Date(),"测试"));
    }

    @Test
    public void test5() {
        Album album = albumService.selectOne1("1");
        System.out.println(album);
    }
    @Test
    public void test6() {
        Integer integer = bannerService.totalCount();
        System.out.println(integer);
    }
    @Test
    public void test7() {
        Integer integer = albumService.totalcount2();
        System.out.println(integer);
    }
    @Test
    public void test8() {
        albumDao.add2(new Chapter("6","...","xxx",199.0,20.0,new Date(),"3"));
    }
    @Test
    public void test9() {
        albumDao.update1(new Album("4","测试4",null,79.0,"xxx","xxx",30,"xxx",new Date()));
    }
    @Test
    public void test10() {
        albumDao.update2(new Chapter("6","...",null,155.0,20.0,null,"3"));
    }
    @Test
    public void test11() {
        List<Chapter> chapters = albumDao.albumJoin(1, 2, "3");
        System.out.println(chapters);
    }
    @Test
    public void test12() {
        Integer integer = albumDao.totalcount3("3");
        System.out.println(integer);
    }
    @Test
    public void test13() {
        List<UserMonth> male = userDao.selectmonth("8", "male");
        System.out.println(male);
    }
    @Test
    public void test14() {
        List<UserLocation> list = userDao.selectlocaltion("male");
        System.out.println(list);
    }
}

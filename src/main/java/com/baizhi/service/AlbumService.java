package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;

import java.util.List;

public interface AlbumService {
    //专辑方法
    public List<Album> showAllAlbum(Integer page, Integer rows);

    public String add1(Album album);

    public void del1(String id);

    public void update1(Album album);

    public Album selectOne1(String id);

    public Integer totalcount1();

    //章节方法
    public List<Chapter> showAllChapter(Integer page, Integer rows);

    public String add2(Chapter chapter,String albumId);

    public void del2(String id);

    public void update2(Chapter chapter);

    public Chapter selectOne2(String id);

    public Integer totalcount2();

    //拼接
    public List<Chapter> albumJoin(Integer page,Integer rows,String rowId);
    public   Integer  totalcount3(String id);
}

package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
@Mapper
@Component("albumDao")
public interface AlbumDao {
    //专辑方法
    public List<Album> showAllAlbum(@Param("page") Integer page, @Param("rows") Integer rows);
    public void add1(Album album);
    public void del1(String id);
    public void update1(Album album);
    public Album selectOne1(String id);
    public   Integer  totalcount1();

    //章节方法
    public List<Chapter> showAllChapter(@Param("page") Integer page, @Param("rows") Integer rows);
    public void add2(Chapter chapter);
    public void del2(String id);
    public void update2(Chapter chapter);
    public Chapter selectOne2(String id);
    public   Integer  totalcount2();

    //拼接表
    public List<Chapter> albumJoin(@Param("page") Integer page, @Param("rows") Integer rows,@Param("rowId") String rowId);
    public   Integer  totalcount3(String id);
}

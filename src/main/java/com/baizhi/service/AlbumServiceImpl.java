package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> showAllAlbum(Integer page, Integer rows) {
        List<Album> albums = albumDao.showAllAlbum(page, rows);
        return albums;
    }

    @Override
    public String add1(Album album) {
        album.setId(UUIDUtil.getUUID());
        album.setPub_date(new Date());
        albumDao.add1(album);
        //必须返回uuid，用此id修改路径
        return album.getId();
    }

    @Override
    public void del1(String id) {
        albumDao.del1(id);
    }

    @Override
    public void update1(Album album) {
        albumDao.update1(album);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Album selectOne1(String id) {
        Album album = albumDao.selectOne1(id);
        return album;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalcount1() {
        Integer integer = albumDao.totalcount1();
        return integer;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> showAllChapter(Integer page, Integer rows) {
        List<Chapter> chapters = albumDao.showAllChapter(page, rows);
        return chapters;
    }

    @Override
    public String add2(Chapter chapter,String albumId) {
        chapter.setId(UUIDUtil.getUUID());
        chapter.setUp_date(new Date());
        chapter.setAlbum_id(albumId);
        //set大小长短
        albumDao.add2(chapter);
        //必须返回uuid，用此id修改路径
        return chapter.getId();
    }

    @Override
    public void del2(String id) {
        albumDao.del2(id);
    }

    @Override
    public void update2(Chapter chapter) {
        albumDao.update2(chapter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Chapter selectOne2(String id) {
        Chapter chapter = albumDao.selectOne2(id);
        return chapter;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer totalcount2() {
        Integer integer = albumDao.totalcount2();
        return integer;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> albumJoin(Integer page, Integer rows, String rowId) {
        List<Chapter> chapters = albumDao.albumJoin(page, rows, rowId);
        return chapters;
    }

    @Override
    public Integer totalcount3(String id) {
        Integer integer = albumDao.totalcount3(id);
        return integer;
    }
}

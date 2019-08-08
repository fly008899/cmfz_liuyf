package com.baizhi.action;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/album")
public class AlbumAction {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("/showAll1")
    @ResponseBody
    public Map<String,Object> showAll(Integer page, Integer rows){
        //当前页的数据
        List<Album> list = albumService.showAllAlbum(page,rows);
        //查询总条数
        Integer totalCount = albumService.totalcount1();
        Map<String,Object> maps=new HashMap<String,Object>();
        //当前页号
        maps.put("page",page);
        //总条数
        maps.put("records",totalCount);
        //总页数
        Integer  pageCount=0;
        if (totalCount%rows==0){
            pageCount=totalCount/rows;
        }else {
            pageCount=totalCount/rows+1;
        }
        maps.put("total",pageCount);
        //每页具体的数据
        maps.put("rows",list);
        return   maps;
    }

    @RequestMapping("/showAll2")
    @ResponseBody
    public Map<String,Object> showAll2(Integer page, Integer rows,String albumId){
        //当前页的数据
        List<Chapter> list = albumService.albumJoin(page,rows,albumId);
        //查询总条数
        Integer totalCount = albumService.totalcount3(albumId);
        Map<String,Object> maps=new HashMap<String,Object>();
        //当前页号
        maps.put("page",page);
        //总条数
        maps.put("records",totalCount);
        //总页数
        Integer  pageCount=0;
        if (totalCount%rows==0){
            pageCount=totalCount/rows;
        }else {
            pageCount=totalCount/rows+1;
        }
        maps.put("total",pageCount);
        //每页具体的数据
        maps.put("rows",list);
        return   maps;
    }

    @RequestMapping("/editAlbum")
    @ResponseBody
    public String editAlbum( Album album, String oper){
        String uuid = null;
        //执行添加方法
        if(oper.equals("add")){
            uuid = albumService.add1(album);
        }
        //执行修改方法
        if(oper.equals("edit")){
            if(album.getCover_img()==""){
                Album album1 = albumService.selectOne1(album.getId());
                album.setCover_img(album1.getCover_img());
            }
            albumService.update1(album);
        }
        //执行删除方法
        if(oper.equals("del")){
            //先删除父目录下所有子文件
            List<Chapter> chapters = albumService.albumJoin(1, 100, album.getId());
            for (Chapter chapter : chapters) {
                albumService.del2(chapter.getId());
            }
            //再删除父目录
            albumService.del1(album.getId());
        }
        return uuid;
    }

    @RequestMapping("/uploadAlbum")
    public void uploadAlbum(MultipartFile cover_img, String id, HttpServletRequest request){
        //获取要上传文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        //获取文件夹
        File file = new File(realPath);
        //创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        //获取上传文件的名字
        String filename = cover_img.getOriginalFilename();
        String name = new Date().getTime()+"_"+filename;
        //文件上传
        try {
            cover_img.transferTo(new File(realPath,name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(id);
        album.setCover_img(name);
        //做修改,修改默认的图片路径
        albumService.update1(album);
    }

    @RequestMapping("/editChapter")
    @ResponseBody
    public String editChapter( Chapter chapter, String oper,String albumId){
        String uuid = null;
        //执行添加方法
        if(oper.equals("add")){
            uuid = albumService.add2(chapter,albumId);
        }
        //执行修改方法
        if(oper.equals("edit")){
            if(chapter.getUrl()==""){
                Chapter chapter1 = albumService.selectOne2(chapter.getId());
                chapter.setUrl(chapter1.getUrl());
                //或者将空字符设为null
            }
            albumService.update2(chapter);
        }
        //执行删除方法
        if(oper.equals("del")){
            albumService.del2(chapter.getId());
        }
        return uuid;
    }

    @RequestMapping("/uploadVideo")
    public void uploadVideo(MultipartFile url, String id, HttpServletRequest request){
        //获取要上传文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/Video");
        //获取文件夹
        File file = new File(realPath);
        //创建文件夹
        if(!file.exists()){
            file.mkdirs();
        }
        //获取上传文件的名字
        String filename = url.getOriginalFilename();
        String name = new Date().getTime()+"_"+filename;
        //文件上传
        Double duration=0.0;
        try {
            url.transferTo(new File(realPath,name));
            //获取文件长短
            AudioFile audioFile = AudioFileIO.read(new File(realPath, name));
            AudioHeader audioHeader = audioFile.getAudioHeader();
            double length = audioHeader.getTrackLength();
            duration=length/60;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取文件大小
        long size = url.getSize();
        String str = String.valueOf(size);
        Double dd = Double.valueOf(str)/1024/1024;

        Chapter chapter = new Chapter();
        chapter.setSize(dd);
        chapter.setDuration(duration);
        chapter.setId(id);
        chapter.setUrl(name);
        //做修改,修改默认的文件路径
        albumService.update2(chapter);
    }
    @RequestMapping("/down")
    public ResponseEntity<byte[]> down(String filename, HttpSession session) throws Exception{
        //获取目标upload的路径
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath("/upload/Video");
        //file没有则创建
        File file = new File(realPath + "/" + filename);
        //把文件转换为字节数组
        byte[] bytes = FileUtils.readFileToByteArray(file);
        //设置下载时的请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        //解决下载文件名乱码问题
        String downname = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
        //设置以附件形式下载
        httpHeaders.setContentDispositionFormData("attachment",downname);
        //应用流的方式配置
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //告知SpringMvc框架状态已经准备好
        return new ResponseEntity<byte[]>(bytes,httpHeaders, HttpStatus.CREATED);


    }



}

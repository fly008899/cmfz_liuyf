package com.baizhi.entity;

import java.util.Date;

public class Article {
    private String id;
    private String title;
    private String picture;
    private String content;
    private String status;
    private Date creat_time;
    private String guru_id;

    public Article() {
    }

    public Article(String id, String title, String picture, String content, String status, Date creat_time, String guru_id) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.content = content;
        this.status = status;
        this.creat_time = creat_time;
        this.guru_id = guru_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    public String getGuru_id() {
        return guru_id;
    }

    public void setGuru_id(String guru_id) {
        this.guru_id = guru_id;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", creat_time=" + creat_time +
                ", guru_id='" + guru_id + '\'' +
                '}';
    }
}

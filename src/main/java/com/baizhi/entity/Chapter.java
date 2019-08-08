package com.baizhi.entity;

import java.util.Date;

public class Chapter {
    private String id;
    private String name;
    private String url;
    private Double size;
    private Double duration;
    private Date up_date;
    private String album_id;

    public Chapter() {
    }

    public Chapter(String id, String name, String url, Double size, Double duration, Date up_date, String album_id) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.size = size;
        this.duration = duration;
        this.up_date = up_date;
        this.album_id = album_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", up_date=" + up_date +
                ", album_id='" + album_id + '\'' +
                '}';
    }
}

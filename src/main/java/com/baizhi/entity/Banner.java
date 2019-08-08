package com.baizhi.entity;

import java.util.Date;

public class Banner {
    private String id;
    private String name;
    private String pic_path;
    private String status;
    private Date creat_time;
    private String description;

    public Banner() {
    }

    public Banner(String id, String name, String pic_path, String status, Date creat_time, String description) {
        this.id = id;
        this.name = name;
        this.pic_path = pic_path;
        this.status = status;
        this.creat_time = creat_time;
        this.description = description;
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

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pic_path='" + pic_path + '\'' +
                ", status='" + status + '\'' +
                ", creat_time=" + creat_time +
                ", description='" + description + '\'' +
                '}';
    }
}

package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class User {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "手机")
    private String phone;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "salt")
    private String salt;
    @Excel(name = "头像")
    private String picture;
    @Excel(name = "昵称")
    private String nick;
    @Excel(name = "用户名")
    private String name;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "地址")
    private String localtion;
    @Excel(name = "个性签名")
    private String sign;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "创建时间")
    private Date creat_time;
    @Excel(name = "上师ID")
    private String guru_id;

    public User() {
    }

    public User(String id, String phone, String password, String salt, String picture, String nick, String name, String sex, String localtion, String sign, String status, Date creat_time, String guru_id) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.salt = salt;
        this.picture = picture;
        this.nick = nick;
        this.name = name;
        this.sex = sex;
        this.localtion = localtion;
        this.sign = sign;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocaltion() {
        return localtion;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
        return "User{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", picture='" + picture + '\'' +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", localtion='" + localtion + '\'' +
                ", sign='" + sign + '\'' +
                ", status='" + status + '\'' +
                ", creat_time=" + creat_time +
                ", guru_id='" + guru_id + '\'' +
                '}';
    }
}

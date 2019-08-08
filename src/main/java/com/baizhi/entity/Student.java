package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class Student {
    @Excel(name = "ID")
    private String id;
    @Excel(name = "图片",type =2,width = 20)
    private String name;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name="生日",format="yyyy-MM-dd",width=20)
    private Date bir;

    public Student() {
    }

    public Student(String id, String name, Integer age, Date bir) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bir = bir;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBir() {
        return bir;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bir=" + bir +
                '}';
    }
}

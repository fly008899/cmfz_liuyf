package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import java.util.List;


public class Teacher {
    @ExcelIgnore
    private String id;
    @Excel(name = "教师姓名",needMerge=true)
    private String name;
    @ExcelCollection(name = "学生")
    private List<Student> students;

    public Teacher() {
    }

    public Teacher(String id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }
}

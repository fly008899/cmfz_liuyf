package com.baizhi.entity;

import java.util.List;


public class Pro {

    private String title;
    private List<City> citys;

    public Pro() {
    }

    public Pro(String title, List<City> citys) {
        this.title = title;
        this.citys = citys;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return "Pro{" +
                "title='" + title + '\'' +
                ", citys=" + citys +
                '}';
    }
}

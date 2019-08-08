package com.baizhi.entity;

public class UserMonth {
    private String month;
    private Integer count;

    public UserMonth() {
    }

    public UserMonth(String month, Integer count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserMonth{" +
                "month='" + month + '\'' +
                ", count=" + count +
                '}';
    }
}


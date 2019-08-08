package com.baizhi.entity;

public class UserLocation {
    private String localtion;
    private Integer count;

    public UserLocation() {
    }

    public UserLocation(String localtion, Integer count) {
        this.localtion = localtion;
        this.count = count;
    }

    public String getLocaltion() {
        return localtion;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserLocation{" +
                "localtion='" + localtion + '\'' +
                ", count=" + count +
                '}';
    }
}

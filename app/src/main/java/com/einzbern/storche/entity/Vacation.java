package com.einzbern.storche.entity;

/**
 * Created by Administrator on 2017/11/21.
 */

public class Vacation {
    private String id;
    private String name;
    // "yyyy-mm-dd"
    private String startDay;
    // "yyyy-mm-dd"
    private String endDay;

    public Vacation() {
    }

    public Vacation(String id, String name, String startDay, String endDay) {
        this.id = id;
        this.name = name;
        this.startDay = startDay;
        this.endDay = endDay;
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

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}

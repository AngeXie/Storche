package com.einzbern.storche.entity;

/**
 * Created by Administrator on 2017/11/21.
 */

public class Exam {
    private String id;
    private String name;
    // "yyyy-mm-dd"
    private String date;
    // "hh-mm"
    private String startTime;
    // "hh-mm"
    private String endTime;

    public Exam(){}

    public Exam(String id, String name, String date, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

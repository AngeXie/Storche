package com.einzbern.storche.entity;

/**
 * Created by Administrator on 2017/11/21.
 */

public class DayMsg {
    private String id;
    private String title;
    // "yyyy-mm-dd"
    private String date;
    private String detail;

    public DayMsg(){}

    public DayMsg(String id, String title, String date, String detail) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

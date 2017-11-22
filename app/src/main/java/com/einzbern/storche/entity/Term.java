package com.einzbern.storche.entity;

/**
 * Created by Administrator on 2017/11/21.
 */

public class Term {
    private String id;
    // "大三-上"
    private String describe;
    // "yyyy-mm-dd"
    private String startDay;
    // "yyyy-mm-dd"
    private String endDay;

    public Term(){}

    public Term(String id, String describe, String startDay, String endDay) {
        this.id = id;
        this.describe = describe;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

package com.einzbern.storche.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/15.
 */

public class Course {
    public final static int MORNING_CLASS1 = 1;
    public final static int MORNING_CLASS2 = 2;
    public final static int NOON_CLASS1 = 3;
    public final static int NOON_CLASS2 = 4;

    public final static int DAY = 0;
    public final static int TIME = 1;
    public final static int SPOT = 2;

    private String id;
    private String name;
    private ArrayList<int[]> time_spot;
    private int startWeek;
    private int endWeek;

    public Course() {}

    public Course(String name, ArrayList<int[]> time_spot, int startWeek, int endWeek) {
        this.name = name;
        this.time_spot = time_spot;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<int[]> getTime_spot() {
        return time_spot;
    }

    public void setTime_spot(ArrayList<int[]> time_spot) {
        this.time_spot = time_spot;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
